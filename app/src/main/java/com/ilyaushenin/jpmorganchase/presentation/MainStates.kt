package com.ilyaushenin.jpmorganchase.presentation

import com.ilyaushenin.jpmorganchase.data.user.User

data class MainStates(
    val loading: Boolean = false,
    val user: User? = null
)