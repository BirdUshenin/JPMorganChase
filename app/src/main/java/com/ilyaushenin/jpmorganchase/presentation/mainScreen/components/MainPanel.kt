package com.ilyaushenin.jpmorganchase.presentation.mainScreen.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.ilyaushenin.features.flexDrag.data.ItemsDropList
import com.ilyaushenin.features.flexDrag.data.move
import com.ilyaushenin.features.flexDrag.horizontal.FlexDragRow
import com.ilyaushenin.features.flexDrag.ui.textStyles.textStylesMainScreen
import com.ilyaushenin.jpmorganchase.R

@Composable
fun MainPanel(
    states: MainStates,
    scrollOffset: Float
) {
    val containerHeight: Dp by animateDpAsState(
        targetValue = when {
            scrollOffset < 0 -> 0.dp
            else -> 300.dp
        },
        animationSpec = tween(durationMillis = 300), label = ""
    )
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
        Test()
    }
}

@Composable
fun Test() {
    val listItems = remember {
        mutableStateListOf(
            ItemsDropList("Favorite Attractiveness", R.drawable.favorite),
            ItemsDropList("Tram", R.drawable.tram),
            ItemsDropList("MacDonald", R.drawable.mac),
            ItemsDropList("Plane Mode", R.drawable.airplanemode),
            ItemsDropList("Code", R.drawable.code),
            ItemsDropList("QR Code", R.drawable.qr_code),
            ItemsDropList("Magic", R.drawable.magic),
            ItemsDropList("Electric Car", R.drawable.electric_car),
            ItemsDropList("Fire Place", R.drawable.fireplace),
            ItemsDropList("Snow Mobile", R.drawable.snowmobile),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        FlexDragRow(
            modifier = Modifier.wrapContentSize(),
            items = listItems,
            colorBox = Color(0xFFECECEC),
            textColor = Color(0xFF000748),
            textStyle = textStylesMainScreen(),
            onMove = { fromIndex, toIndex ->
                listItems.move(fromIndex, toIndex)
            }
        )
    }
}