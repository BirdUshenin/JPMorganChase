package com.ilyaushenin.jpmorganchase.data

data class User(
    val name: String,
    val cards: List<Cards>
)