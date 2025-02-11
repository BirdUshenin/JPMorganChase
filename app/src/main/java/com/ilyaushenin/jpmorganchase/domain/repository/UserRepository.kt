package com.ilyaushenin.jpmorganchase.domain.repository

import com.ilyaushenin.jpmorganchase.data.user.User

interface UserRepository {
    suspend fun getUser(): User
}