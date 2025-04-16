package com.ilyaushenin.jpmorganchase.data.mainContent

data class Offers(
    val id: String,
    val offerList: List<Offer>
)

data class Offer (
    val id: String,
    val name: String,
    val icon: String
)