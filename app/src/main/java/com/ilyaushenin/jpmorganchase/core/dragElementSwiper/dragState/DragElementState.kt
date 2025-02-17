package com.ilyaushenin.jpmorganchase.core.dragElementSwiper.dragState

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.rememberDragElementState.SpringDragCancelledAnimation
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.data.ItemPosition
import kotlinx.coroutines.CoroutineScope

class DragElementState(
    val listState: LazyListState,
    scope: CoroutineScope,
    maxScrollPerFrame: Float,
    onMove: (fromIndex: ItemPosition, toIndex: ItemPosition) -> (Unit),
    canDragOver: ((draggedOver: ItemPosition, dragging: ItemPosition) -> Boolean)? = null,
    onDragEnd: ((startIndex: Int, endIndex: Int) -> (Unit))? = null,
    dragCancelledAnimation: DragCancelledAnimation = SpringDragCancelledAnimation()
) : DragState<LazyListItemInfo>(
    scope,
    maxScrollPerFrame,
    onMove,
    canDragOver,
    onDragEnd,
    dragCancelledAnimation
) {
    override val isVerticalScroll: Boolean
        get() = listState.layoutInfo.orientation == Orientation.Vertical
    override val LazyListItemInfo.left: Int
        get() = when {
            isVerticalScroll -> 0
            listState.layoutInfo.reverseLayout -> listState.layoutInfo.viewportSize.width - offset - size
            else -> offset
        }
    override val LazyListItemInfo.top: Int
        get() = when {
            !isVerticalScroll -> 0
            listState.layoutInfo.reverseLayout -> listState.layoutInfo.viewportSize.height - offset - size
            else -> offset
        }
    override val LazyListItemInfo.right: Int
        get() = when {
            isVerticalScroll -> 0
            listState.layoutInfo.reverseLayout -> listState.layoutInfo.viewportSize.width - offset
            else -> offset + size
        }
    override val LazyListItemInfo.bottom: Int
        get() = when {
            !isVerticalScroll -> 0
            listState.layoutInfo.reverseLayout -> listState.layoutInfo.viewportSize.height - offset
            else -> offset + size
        }
    override val LazyListItemInfo.width: Int
        get() = if (isVerticalScroll) 0 else size
    override val LazyListItemInfo.height: Int
        get() = if (isVerticalScroll) size else 0
    override val LazyListItemInfo.itemIndex: Int
        get() = index
    override val LazyListItemInfo.itemKey: Any
        get() = key
    override val visibleItemsInfo: List<LazyListItemInfo>
        get() = listState.layoutInfo.visibleItemsInfo
    override val viewportStartOffset: Int
        get() = listState.layoutInfo.viewportStartOffset
    override val viewportEndOffset: Int
        get() = listState.layoutInfo.viewportEndOffset
    override val firstVisibleItemIndex: Int
        get() = listState.firstVisibleItemIndex
    override val firstVisibleItemScrollOffset: Int
        get() = listState.firstVisibleItemScrollOffset

    override suspend fun scrollToItem(index: Int, offset: Int) {
        listState.scrollToItem(index, offset)
    }

    override fun onDragStart(offsetX: Int, offsetY: Int): Boolean =
        if (isVerticalScroll) {
            super.onDragStart(0, offsetY)
        } else {
            super.onDragStart(offsetX, 0)
        }

    override fun findTargets(x: Int, y: Int, selected: LazyListItemInfo) =
        if (isVerticalScroll) {
            super.findTargets(0, y, selected)
        } else {
            super.findTargets(x, 0, selected)
        }

    override fun chooseDropItem(
        draggedItemInfo: LazyListItemInfo?,
        items: List<LazyListItemInfo>,
        curX: Int,
        curY: Int
    ) =
        if (isVerticalScroll) {
            super.chooseDropItem(draggedItemInfo, items, 0, curY)
        } else {
            super.chooseDropItem(draggedItemInfo, items, curX, 0)
        }
}