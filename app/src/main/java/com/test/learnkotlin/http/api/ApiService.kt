package com.test.learnkotlin.http.api

import com.test.learnkotlin.bean.LoginResponse
import com.test.learnkotlin.http.HttpUrl
import com.test.learnkotlin.http.ResponseEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(HttpUrl.LOGIN)
    suspend fun login(@Body params: Map<String, String>):ResponseEntity<LoginResponse>
}