package com.ilyaushenin.jpmorganchase.data.repository

import com.ilyaushenin.jpmorganchase.data.user.User
import com.ilyaushenin.jpmorganchase.data.network.ApiService
import com.ilyaushenin.jpmorganchase.domain.repository.UserRepository

class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun getUser(): User {
        return apiService.getUserResponse().user
    }
}