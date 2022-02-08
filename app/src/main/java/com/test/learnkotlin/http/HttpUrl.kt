package com.test.learnkotlin.http

object HttpUrl {
    const val BASE_URL_PRODUCT = "https://app.granayudaglobal.com/granayudaglobal/multi/"
    private const val BASE_URL_COMMON = "api/v1"
    const val LOGIN = "api/login/v1/register"
    const val HOME = "$BASE_URL_COMMON/home"
    const val MINE = "$BASE_URL_COMMON/mine"
    const val SMS_CODE = "$BASE_URL_COMMON/sms/code"
}