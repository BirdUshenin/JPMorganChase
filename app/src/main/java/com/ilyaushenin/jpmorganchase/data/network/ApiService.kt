package com.ilyaushenin.jpmorganchase.data.network

import com.ilyaushenin.jpmorganchase.data.user.UserResponse
import retrofit2.http.GET

interface ApiService {

    @GET("user.json")
    suspend fun getUserResponse(): UserResponse

}