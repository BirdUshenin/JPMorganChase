package com.ilyaushenin.jpmorganchase.data.user

data class Cards(
    val id: Int,
    val balance: Int?,
    val currency: String,
    val number: String,
    val name: String?
)