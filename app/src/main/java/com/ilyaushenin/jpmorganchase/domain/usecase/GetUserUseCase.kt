package com.ilyaushenin.jpmorganchase.domain.usecase

import com.ilyaushenin.jpmorganchase.data.user.User
import com.ilyaushenin.jpmorganchase.domain.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository) {
    suspend fun execute(): User {
        return repository.getUser()
    }
}