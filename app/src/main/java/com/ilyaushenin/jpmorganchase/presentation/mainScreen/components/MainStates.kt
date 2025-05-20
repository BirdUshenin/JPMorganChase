package com.ilyaushenin.jpmorganchase.presentation.mainScreen.components

import com.ilyaushenin.jpmorganchase.data.mainContent.Offers
import com.ilyaushenin.jpmorganchase.data.user.User

data class MainStates(
    val loading: Boolean = false,
    val user: User? = null,
    val offers: Offers? = null
)