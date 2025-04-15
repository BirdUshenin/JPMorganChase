package com.ilyaushenin.jpmorganchase.domain.di

import com.ilyaushenin.jpmorganchase.data.repository.UserRepositoryImpl
import com.ilyaushenin.jpmorganchase.domain.repository.UserRepository
import com.ilyaushenin.jpmorganchase.domain.usecase.GetUserUseCase
import org.koin.dsl.module

val repositoryModule = module {

    single<UserRepository> { UserRepositoryImpl(get()) }

    single { GetUserUseCase(get()) }
}
