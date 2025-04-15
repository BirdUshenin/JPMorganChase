package com.ilyaushenin.jpmorganchase.di

import com.ilyaushenin.jpmorganchase.data.network.di.apiModule
import com.ilyaushenin.jpmorganchase.presentation.di.repositoryModule
import com.ilyaushenin.jpmorganchase.presentation.di.viewModelsModule
import org.koin.dsl.module

val appModule = module {
    includes(
        apiModule,
        repositoryModule,
        viewModelsModule
    )
}