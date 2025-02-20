package com.ilyaushenin.jpmorganchase.presentation.mainScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.dragState.DragState
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.rememberDragElementState.DragElementItem
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.rememberDragElementState.detectReorderAfterLongPress
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.rememberDragElementState.dragElementer
import com.ilyaushenin.jpmorganchase.core.dragElementSwiper.rememberDragElementState.rememberDragElementState
import com.ilyaushenin.jpmorganchase.presentation.theme.JPMorganChaseTheme

@Composable
fun MainScreen(
    states: MainStates
) {
    var scrollOffset by remember { mutableFloatStateOf(0f) }

    val containerHeight: Dp by animateDpAsState(
        targetValue = when {
            scrollOffset < 0 -> 0.dp
            else -> 300.dp
        },
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    scrollOffset += available.y
                    return Offset.Zero
                }
            })
    ) {

        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(containerHeight)
                .zIndex(1f)
                .background(Color(0xFF2551A2))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF6A85C0))
                        .weight(1f)
                        .height(50.dp)
                ) { }
                IconButton(modifier = Modifier
                    .size(50.dp),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                IconButton(modifier = Modifier
                    .size(50.dp),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Face,
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    text = "Hi, ${states.user?.name}",
                    fontSize = 25.sp,
                    color = Color.White
                )
            }

            HorizontalReorderList()
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(List(100) { "Элемент ${it + 1}" }) { item ->
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = item)
                }
            }
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JPMorganChaseTheme {
        MainScreen(
            states = MainStates()
        )
    }
}