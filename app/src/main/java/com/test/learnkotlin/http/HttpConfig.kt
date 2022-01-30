package com.test.learnkotlin.http

import com.test.learnkotlin.app.App
import com.test.learnkotlin.utils.AppUtils
import com.test.learnkotlin.utils.OSUtils
import com.test.learnkotlin.utils.Utility

object HttpConfig {
    const val KEY_TOKEN = "autor"
    /* fun getHttpHeaders(): Map<String, String> = mutableMapOf(
         KEY_TOKEN to "",
         "deviceType" to "android",
         "imei" to "0000000000000000000000000",
         "systemVersion" to OSUtils.getRelease(),
         "deviceModel" to Utility.getDeviceModel(),
         "versionCode" to AppUtils.getVersionCode(App.appContext).toString(),
         "versionName" to AppUtils.getVersionName(App.appContext),
         "manufacturerVersionName" to Utility.getManufacturerVersionName(),

         )*/

    fun getHttpHeaders(): Map<String, String> = mutableMapOf(
        "tk" to "",
        "ck" to "Mainchannel",
        "dm" to "MI 8 SE",
        "dt" to "1",
        "pAppId" to "Granayuda",
        "lang" to "es_MX",
        "lng" to "0",
        "lat" to "0",
        "sv" to "10",
        "avc" to "1",
        "avn" to "1.0.0",
        "imei" to "D50B078F-DBC2-35F1-B629-AAA719944741",
        "sn" to "Xiaomi",
        "apn" to "com.granayuda.mobile",
    )
}