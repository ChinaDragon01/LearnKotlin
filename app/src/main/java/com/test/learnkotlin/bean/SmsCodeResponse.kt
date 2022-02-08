package com.test.learnkotlin.bean

import com.google.gson.annotations.SerializedName

class SmsCodeResponse {
    @SerializedName("needValidate")
    var needValidate: Boolean = false

    @SerializedName("imageVerifyCode")
    var imageVerifyCode: ImageVerifyCode? = null

    class ImageVerifyCode {
        @SerializedName("bigImage")
        var bigImage: String? = null

        @SerializedName("cutImage")
        var cutImage: String? = null

        @SerializedName("precision")
        var precision: Int = 0

        @SerializedName("width")
        var width: Int = 0

        @SerializedName("height")
        var height: Int = 0

        @SerializedName("x")
        var x: Int = 0

        @SerializedName("y")
        var y: Int = 0

    }


}