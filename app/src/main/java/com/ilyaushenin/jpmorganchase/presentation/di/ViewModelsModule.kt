package com.ilyaushenin.jpmorganchase.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.ilyaushenin.jpmorganchase.presentation.mainScreen.MainViewModel

val viewModelsModule = module {
    viewModelOf(::MainViewModel)
}
