package com.test.learnkotlin.model

import com.test.learnkotlin.base.BaseModel
import com.test.learnkotlin.bean.LoginResponse
import com.test.learnkotlin.http.ResponseEntity
import com.test.learnkotlin.http.api.ApiService

class LoginModel : BaseModel() {

    suspend fun login(
        registerPhone: String,
        verifyCode: String,
        x: String,
        y: String
    ): ResponseEntity<LoginResponse> {
        return createService(ApiService::class.java)
            .login(
                mapOf(
                    "registerPhone" to registerPhone,
                    "verifyCode" to verifyCode,
                    "x" to x,
                    "y" to y,
                ).filterValues { it.isNotBlank() }
            )
    }
}