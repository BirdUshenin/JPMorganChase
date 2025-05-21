package com.ilyaushenin.features.flexDrag.horizontal

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import kotlinx.coroutines.launch

@Composable
fun Modifier.longPressChecker(
    dragDropListState: FlexDragRowState,
): Modifier {
    val scope = rememberCoroutineScope()
    val hapticFeedback = LocalHapticFeedback.current
    return this.then(
        pointerInput(Unit) {
            detectDragGesturesAfterLongPress(
                onDrag = { change, offset ->
                    change.consume()
                    dragDropListState.onDrag(offset = offset)
                    val overScroll = dragDropListState.checkForOverScroll()
                    if (overScroll != 0f) {
                        scope.launch {
                            dragDropListState.lazyListState.scrollBy(overScroll)
                        }
                    }
                },
                onDragStart = {
                        offset -> dragDropListState.onDragStart(offset)
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                },
                onDragEnd = { dragDropListState.onDragInterrupted() },
                onDragCancel = { dragDropListState.onDragInterrupted() }
            )
        }
    )
}