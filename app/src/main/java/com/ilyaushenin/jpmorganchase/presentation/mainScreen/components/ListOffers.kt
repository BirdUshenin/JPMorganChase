package com.ilyaushenin.jpmorganchase.presentation.mainScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListOffers(
    states: MainStates
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Chase Offers",
                    fontSize = 16.sp
                )
                Text(
                    text = "Add deals, shop and get money back.",
                    fontSize = 12.sp
                )
            }
            Text(
                text = "All others",
                modifier = Modifier,
            )
        }
        LazyRow {
            items(states.offers?.offerList ?: emptyList()) { offer ->
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(100.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = offer.name, color = Color.Black)
                }
            }
        }
    }
}