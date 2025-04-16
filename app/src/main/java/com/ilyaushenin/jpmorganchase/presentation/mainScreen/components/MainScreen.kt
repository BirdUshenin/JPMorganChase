package com.ilyaushenin.jpmorganchase.presentation.mainScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            item {
                ListOffers(states = states)
            }
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