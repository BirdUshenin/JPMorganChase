package com.ilyaushenin.jpmorganchase.core.dragElementSwiper.rememberDragElementState

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerId
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.isOutOfBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAll
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.util.fastFirstOrNull
import androidx.compose.ui.zIndex
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.data.ItemPosition
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.data.StartDrag
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.dragState.DragCancelledAnimation
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.dragState.DragElementState
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.dragState.DragState
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

@Composable
fun rememberDragElementState(
    onMove: (ItemPosition, ItemPosition) -> Unit,
    listState: LazyListState = rememberLazyListState(),
    canDragOver: ((draggedOver: ItemPosition, dragging: ItemPosition) -> Boolean)? = null,
    onDragEnd: ((startIndex: Int, endIndex: Int) -> (Unit))? = null,
    maxScrollPerFrame: Dp = 20.dp,
    dragCancelledAnimation: DragCancelledAnimation = SpringDragCancelledAnimation()
): DragElementState {
    val maxScroll = with(LocalDensity.current) { maxScrollPerFrame.toPx() }
    val scope = rememberCoroutineScope()
    val state = remember(listState) {
        DragElementState(
            listState,
            scope,
            maxScroll,
            onMove,
            canDragOver,
            onDragEnd,
            dragCancelledAnimation
        )
    }
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    LaunchedEffect(state) {
        state.visibleItemsChanged()
            .collect { state.onDrag(0, 0) }
    }

    LaunchedEffect(state) {
        var reverseDirection = !listState.layoutInfo.reverseLayout
        if (isRtl && listState.layoutInfo.orientation != Orientation.Vertical) {
            reverseDirection = !reverseDirection
        }
        val direction = if (reverseDirection) 1f else -1f
        while (true) {
            val diff = state.scrollChannel.receive()
            listState.scrollBy(diff * direction)
        }
    }
    return state
}

@SuppressLint("ReturnFromAwaitPointerEventScope")
fun Modifier.dragElementer(
    state: DragState<*>
) = then(
    Modifier.pointerInput(Unit) {
        forEachGesture {
            val dragStart = state.interactions.receive()
            val down = awaitPointerEventScope {
                currentEvent.changes.fastFirstOrNull { it.id == dragStart.id }
            }
            if (down != null && state.onDragStart(
                    down.position.x.toInt(),
                    down.position.y.toInt()
                )
            ) {
                dragStart.offset?.apply {
                    state.onDrag(x.toInt(), y.toInt())
                }
                detectDrag(
                    down.id,
                    onDragEnd = {
                        state.onDragCanceled()
                    },
                    onDragCancel = {
                        state.onDragCanceled()
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        state.onDrag(dragAmount.x.toInt(), dragAmount.y.toInt())
                    })
            }
        }
    })

internal suspend fun PointerInputScope.detectDrag(
    down: PointerId,
    onDragEnd: () -> Unit = { },
    onDragCancel: () -> Unit = { },
    onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit,
) {
    awaitPointerEventScope {
        if (
            drag(down) {
                onDrag(it, it.positionChange())
                it.consume()
            }
        ) {
            currentEvent.changes.forEach {
                if (it.changedToUp()) it.consume()
            }
            onDragEnd()
        } else {
            onDragCancel()
        }
    }
}

class SpringDragCancelledAnimation(private val stiffness: Float = Spring.StiffnessMediumLow) :
    DragCancelledAnimation {
    private val animatable = Animatable(Offset.Zero, Offset.VectorConverter)
    override val offset: Offset
        get() = animatable.value

    override var position by mutableStateOf<ItemPosition?>(null)
        private set

    override suspend fun dragCancelled(position: ItemPosition, offset: Offset) {
        this.position = position
        animatable.snapTo(offset)
        animatable.animateTo(
            Offset.Zero,
            spring(stiffness = stiffness, visibilityThreshold = Offset.VisibilityThreshold)
        )
        this.position = null
    }
}

@SuppressLint("ReturnFromAwaitPointerEventScope")
@Composable
fun Modifier.detectReorderAfterLongPress(state: DragElementState): Modifier {
    val hapticFeedback = LocalHapticFeedback.current
    return this.then(
        Modifier.pointerInput(Unit) {
            forEachGesture {
                val down = awaitPointerEventScope {
                    awaitFirstDown(requireUnconsumed = false)
                }
                awaitLongPressOrCancellation(down)?.also {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    state.interactions.trySend(StartDrag(down.id))
                }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.DragElementItem(
    dragElementState: DragState<*>,
    key: Any?,
    modifier: Modifier = Modifier,
    index: Int? = null,
    orientationLocked: Boolean = true,
    content: @Composable BoxScope.(isDragging: Boolean) -> Unit
) = DragElementItem(
    dragElementState,
    key,
    modifier,
    Modifier.animateItemPlacement(),
    orientationLocked,
    index,
    content
)

@Composable
fun DragElementItem(
    state: DragState<*>,
    key: Any?,
    modifier: Modifier = Modifier,
    defaultDraggingModifier: Modifier = Modifier,
    orientationLocked: Boolean = true,
    index: Int? = null,
    content: @Composable BoxScope.(isDragging: Boolean) -> Unit
) {
    val isDragging = if (index != null) {
        index == state.draggingItemIndex
    } else {
        key == state.draggingItemKey
    }
    val draggingModifier =
        if (isDragging) {
            Modifier
                .zIndex(1f)
                .graphicsLayer {
                    translationX =
                        if (!orientationLocked || !state.isVerticalScroll) state.draggingItemLeft else 0f
                    translationY =
                        if (!orientationLocked || state.isVerticalScroll) state.draggingItemTop else 0f
                }
        } else {
            val cancel = if (index != null) {
                index == state.dragCancelledAnimation.position?.index
            } else {
                key == state.dragCancelledAnimation.position?.key
            }
            if (cancel) {
                Modifier
                    .zIndex(1f)
                    .graphicsLayer {
                        translationX =
                            if (!orientationLocked || !state.isVerticalScroll) state.dragCancelledAnimation.offset.x else 0f
                        translationY =
                            if (!orientationLocked || state.isVerticalScroll) state.dragCancelledAnimation.offset.y else 0f
                    }
            } else {
                defaultDraggingModifier
            }
        }
    Box(modifier = modifier.then(draggingModifier)) {
        content(isDragging)
    }
}

@SuppressLint("ReturnFromAwaitPointerEventScope")
internal suspend fun PointerInputScope.awaitLongPressOrCancellation(
    initialDown: PointerInputChange
): PointerInputChange? {
    var longPress: PointerInputChange? = null
    var currentDown = initialDown
    val longPressTimeout = viewConfiguration.longPressTimeoutMillis
    return try {
        withTimeout(longPressTimeout) {
            awaitPointerEventScope {
                var finished = false
                while (!finished) {
                    val event = awaitPointerEvent(PointerEventPass.Main)
                    val consumeCheck = awaitPointerEvent(PointerEventPass.Final)

                    if (event.changes.fastAll { it.changedToUpIgnoreConsumed() }) {
                        finished = true
                    }
                    if (event.changes.fastAny {
                            it.isConsumed || it.isOutOfBounds(size, extendedTouchPadding)
                        }
                    ) {
                        finished = true
                    }
                    if (consumeCheck.changes.fastAny { it.isConsumed }) {
                        finished = true
                    }
                    if (!event.isPointerUp(currentDown.id)) {
                        longPress = event.changes.fastFirstOrNull { it.id == currentDown.id }
                    } else {
                        val newPressed = event.changes.fastFirstOrNull { it.pressed }
                        if (newPressed != null) {
                            currentDown = newPressed
                            longPress = currentDown
                        } else {
                            finished = true
                        }
                    }
                }
            }
        }
        null
    } catch (_: TimeoutCancellationException) {
        longPress ?: initialDown
    }
}

private fun PointerEvent.isPointerUp(pointerId: PointerId): Boolean =
    changes.fastFirstOrNull { it.id == pointerId }?.pressed != true