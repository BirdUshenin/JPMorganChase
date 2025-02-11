package com.ilyaushenin.jpmorganchase.domain.usecase

import com.ilyaushenin.jpmorganchase.data.user.Cards
import com.ilyaushenin.jpmorganchase.data.user.User
import com.ilyaushenin.jpmorganchase.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions

class TestRepository: UserRepository {
    override suspend fun getUser(): User {
        return User(name = "", cards = listOf(Cards(id = 1, balance = 1, currency = "", number = "", name = "")))
    }
}

class GetUserUseCaseTest {

    @Test
    fun `should return the same data as user repository`() = runBlocking {

        val testRepository = TestRepository()
        val useCase = GetUserUseCase(repository = testRepository)
        val actual = useCase.execute()
        val expected = User(name = "", cards = listOf(Cards(id = 1, balance = 1, currency = "", number = "", name = "")))

        Assertions.assertEquals(expected, actual)

    }

}