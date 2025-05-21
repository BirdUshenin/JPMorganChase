package com.ilyaushenin.features.flexDrag.horizontal

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ilyaushenin.features.flexDrag.data.ItemsDropList
import com.ilyaushenin.features.flexDrag.data.move
import androidx.compose.ui.text.TextStyle
import com.ilyaushenin.features.flexDrag.ui.textStyles.textStylesMainScreen

@Composable
fun FlexDragRow(
    modifier: Modifier = Modifier,
    onMove: (Int, Int) -> Unit,
    colorBox: Color = Color.White,
    textColor: Color = Color.Black,
    textStyle: TextStyle,
    items: List<ItemsDropList>
) {
    val dragDropListState = rememberFlexDragRowState(onMove = onMove)

    LazyRow(
        modifier = modifier
            .longPressChecker(dragDropListState = dragDropListState)
            .wrapContentSize().padding(vertical = 10.dp),
        state = dragDropListState.lazyListState
    ) {
        itemsIndexed(items) { index, item ->
            val animatedTranslationX by animateFloatAsState(
                targetValue = if (index == dragDropListState.currentIndexOfDraggedItem) {
                    dragDropListState.elementDisplacement ?: 0f
                } else {
                    0f
                }, label = "change box animation"
            )
            val isDragged = index == dragDropListState.currentIndexOfDraggedItem
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .size(width = 95.dp, height = 78.dp)
                    .zIndex(if (isDragged) 1f else 0f)
                    .graphicsLayer {
                        translationX = animatedTranslationX
                    }
                    .shadow(
                        elevation = if (isDragged) 10.dp else 0.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(colorBox, shape = RoundedCornerShape(8.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item.imageRes?.let { image ->
                    Image(
                        modifier = Modifier
                            .size(28.dp)
                            .padding(top = 8.dp),
                        painter = painterResource(id = image),
                        contentDescription = null
                    )
                }
                Text(
                    modifier = Modifier.padding(
                        start = 8.dp,
                        bottom = 8.dp,
                        end = 8.dp
                    ),
                    text = item.text,
                    color = textColor,
                    style = textStyle,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DragDropRowPreview(){
    val listItems = remember {
        mutableStateListOf(
            ItemsDropList("Favorite Attractiveness", null),
            ItemsDropList("Tram", null),
            ItemsDropList("MacDonald", null),
            ItemsDropList("Plane Mode", null),
            ItemsDropList("Code", null),
            ItemsDropList("QR Code", null),
            ItemsDropList("Magic", null),
        )
    }
    FlexDragRow(
        modifier = Modifier
            .background(Color.DarkGray),
        colorBox = Color.White,
        items = listItems,
        textStyle = textStylesMainScreen(),
        onMove = { fromIndex, toIndex ->
            listItems.move(fromIndex, toIndex)
        }
    )
}