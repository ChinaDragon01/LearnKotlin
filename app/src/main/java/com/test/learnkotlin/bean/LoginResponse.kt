package com.test.learnkotlin.bean

import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("accessToken")
    var token: String? = null

    @SerializedName("customerExistFlag")
    var customerExistFlag: Boolean = false //是否位新用户注册 true是 false否 true需要跳转到设置登录密码界面

    @SerializedName("customerId")
    var customerId: String? = null//用户唯一id

    @SerializedName("registerPhone")
    var registerPhone: String? = null//用户注册手机号
}