package com.ilyaushenin.jpmorganchase.presentation.mainScreen.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaushenin.jpmorganchase.core.extention.customBrush
import com.ilyaushenin.jpmorganchase.data.user.Cards
import com.ilyaushenin.jpmorganchase.data.user.User
import com.ilyaushenin.jpmorganchase.presentation.theme.JPMorganChaseTheme

@Composable
fun MainScreen(
    states: MainStates
) {
    var scrollOffset by remember { mutableFloatStateOf(0f) }

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

        MainPanel(
            states = states,
            scrollOffset = scrollOffset
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                CardPanel(
                    states = states
                )
            }
        }
    }
}

@Composable
fun CardPanel(
    states: MainStates
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 12.dp
            ),
            text = "Account (2)"
        )
        when {
            states.loading -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .height(20.dp)
                                    .width(120.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .customBrush(500f)
                            ) { }
                            Row(
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .height(20.dp)
                                    .width(50.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .customBrush(500f)
                            ) { }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .height(20.dp)
                            .width(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .customBrush(500f)
                    ) { }
                }
            }
            else -> {
                states.user?.cards?.forEachIndexed { index, card ->
                    DebitCard(
                        states = states,
                        cards = card
                    )
                    if (index < states.user.cards.lastIndex) {
                        HorizontalDivider()
                    }
                }
                Text(
                    modifier = Modifier.padding(
                        vertical = 12.dp,
                        horizontal = 12.dp
                    ),
                    text = "Open an Account"
                )
            }
        }
    }
}

@Composable
fun DebitCard(
    states: MainStates,
    cards: Cards
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column (
            modifier = Modifier
        ){
            if (states.loading){
                Row(
                    modifier = Modifier
                        .height(20.dp)
                        .width(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .customBrush(500f)
                ) { }
            } else {
                Text(cards.name)
                Text("** 9585")
            }
        }
        if (states.loading){
            Row(
                modifier = Modifier
                    .height(20.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .customBrush(500f)
            ) { }
        } else {
            Text(text = "${cards.icon_valuta}${cards.balance}")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JPMorganChaseTheme {
        MainScreen(
            states = MainStates(
                user = User(
                    name = "Ilya",
                    cards = listOf(
                        Cards(
                            id = 541415,
                            balance = "120,985,97",
                            currency = "RUB",
                            icon_valuta = "₽",
                            number = "3113444455559857",
                            name = "PERFBUS SAVINGS"
                        ),
                    )
                ),
            )
        )
    }
}