package com.ilyaushenin.jpmorganchase.presentation.mainScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaushenin.jpmorganchase.core.extention.customBrush
import com.ilyaushenin.jpmorganchase.data.user.Cards

@Composable
fun CardPanel(
    states: MainStates
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))
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

@Preview
@Composable
fun CardsPreview() {
    CardPanel(
        states = MainStates()
    )
}