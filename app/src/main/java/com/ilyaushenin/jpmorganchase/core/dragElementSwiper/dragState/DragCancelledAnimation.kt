package com.ilyaushenin.jpmorganchase.core.dragElementSwiper.dragState

import androidx.compose.ui.geometry.Offset
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.data.ItemPosition

interface DragCancelledAnimation {
    suspend fun dragCancelled(position: ItemPosition, offset: Offset)
    val position: ItemPosition?
    val offset: Offset
}