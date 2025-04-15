package com.ilyaushenin.jpmorganchase.data.network.di

import com.ilyaushenin.jpmorganchase.data.network.NetworkComponent
import org.koin.dsl.module

val apiModule = module {

    single { NetworkComponent() }

    single { get<NetworkComponent>().apiService }
}