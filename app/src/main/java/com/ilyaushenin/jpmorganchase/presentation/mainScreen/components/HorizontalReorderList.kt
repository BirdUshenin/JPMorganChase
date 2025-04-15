package com.ilyaushenin.jpmorganchase.presentation.mainScreen.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.dragState.DragState
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.rememberDragElementState.DragElementItem
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.rememberDragElementState.detectReorderAfterLongPress
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.rememberDragElementState.dragElementer
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.rememberDragElementState.rememberDragElementState

@Composable
fun HorizontalReorderList() {
    val data = remember { mutableStateOf(List(10) { "Item $it" }) }
    val state = rememberDragElementState(onMove = { from, to ->
        data.value = data.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })

    LazyRow(
        state = state.listState,
        modifier = Modifier
            .fillMaxSize()
            .dragElementer(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(data.value, { it }) { item ->
            DragElementItem(state as DragState<*>, key = item) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp, label = "")
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(100.dp)
                        .shadow(elevation.value)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item, color = Color.Black)
                }
            }
        }
    }
}