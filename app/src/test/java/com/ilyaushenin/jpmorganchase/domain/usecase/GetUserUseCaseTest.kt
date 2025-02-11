package com.ilyaushenin.jpmorganchase.domain.usecase

import com.ilyaushenin.jpmorganchase.data.network.ApiService
import com.ilyaushenin.jpmorganchase.data.repository.UserRepositoryImpl
import com.ilyaushenin.jpmorganchase.data.user.Cards
import com.ilyaushenin.jpmorganchase.data.user.User
import com.ilyaushenin.jpmorganchase.data.user.UserResponse
import com.ilyaushenin.jpmorganchase.domain.repository.UserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class TestRepository : UserRepository {
    override suspend fun getUser(): User {
        return User(
            name = "",
            cards = listOf(Cards(id = 1, balance = 1, currency = "", number = "", name = ""))
        )
    }
}

class GetUserUseCaseTest {

    @Mock
    val userRepository = mock<UserRepository>()

    @Test
    fun `should return the same data as user repository`() = runBlocking {

        // simple
        // val testRepository = TestRepository()
        val testData = User(
            name = "",
            cards = listOf(
                Cards(id = 1, balance = 1, currency = "", number = "", name = "")
            )
        )
        // other variant
        // Mockito.`when`(userRepository.getUser()).thenReturn(testData)
        whenever(userRepository.getUser()).thenReturn(testData)

        val useCase = GetUserUseCase(repository = userRepository)
        val actual = useCase.execute()
        val expected = User(
            name = "",
            cards = listOf(
                Cards(id = 1, balance = 1, currency = "", number = "", name = "")
            )
        )
        Assertions.assertEquals(expected, actual)
    }

}

class GetResponseUserDateTest {

    private lateinit var userRepository: UserRepository
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = mock()
        userRepository = UserRepositoryImpl(apiService)
    }

    @Test
    fun `should return user from API`() = runBlocking {
        val testData = UserResponse(
            User(
                name = "",
                cards = listOf(
                    Cards(
                        id = 39031,
                        balance = 500,
                        currency = "RUB",
                        number = "3113444455559857",
                        name = ""
                    ),
                    Cards(
                        id = 431091,
                        balance = null,
                        currency = "USD",
                        number = "3113444455789359",
                        name = ""
                    )
                )
            )
        )

        whenever(apiService.getUserResponse()).thenReturn(testData)

        val result = userRepository.getUser()

        assertEquals(testData.user, result)
    }
}