package com.ilyaushenin.jpmorganchase.domain

import com.ilyaushenin.jpmorganchase.data.ApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("user.json")
    suspend fun getUser(): ApiResponse

}