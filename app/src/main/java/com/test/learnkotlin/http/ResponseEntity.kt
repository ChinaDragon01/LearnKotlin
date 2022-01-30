package com.test.learnkotlin.http

import com.google.gson.annotations.SerializedName
import com.test.learnkotlin.http.exception.HttpException

class ResponseEntity<T> {
    @SerializedName(value = "code")
    val code = 0

    @SerializedName(value = "message")
    val message: String? = null

    @SerializedName(value = "data")
    val data: T? = null

    var exception: HttpException? = null

    fun isSuccessful() = code == 0

    fun isException() = exception != null
}