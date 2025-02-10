package com.ilyaushenin.jpmorganchase.data.user

data class User(
    val name: String,
    val cards: List<Cards>
)