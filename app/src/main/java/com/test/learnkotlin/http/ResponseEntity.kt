package com.test.learnkotlin.http

import com.google.gson.annotations.SerializedName
import com.test.learnkotlin.http.exception.HttpException

class ResponseEntity<T>() {

    @SerializedName(value = "code")
    val code = 0

    @SerializedName(value = "message")
    val message: String? = null

    @SerializedName(value = "data")
    val data: T? = null

    var exception: HttpException? = null

    constructor(handleException: HttpException) : this() {
        this.exception = handleException
    }

    fun isSuccessful():Boolean = code == 0

    /**
     * 判断是否请求失败
     */
    fun isFailure(): Boolean {
        return code != 0 && exception == null
    }

    fun isException():Boolean = exception != null
    override fun toString(): String {
        return "ResponseEntity(code=$code, message=$message, data=$data, exception=$exception)"
    }


}