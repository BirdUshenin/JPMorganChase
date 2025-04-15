package com.ilyaushenin.jpmorganchase.data.user

data class Cards(
    val id: Int,
    val balance: String?,
    val currency: String,
    val icon_valuta: String? = null,
    val number: String,
    val name: String
)