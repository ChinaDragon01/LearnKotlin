package com.test.learnkotlin.utils

//import com.aitime.android.deviceid.DeviceIdentifier
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresPermission
import com.test.learnkotlin.R
import com.test.learnkotlin.app.App
import java.security.MessageDigest
import java.util.*

object Utility {

    private const val MANUFACTURER_XIAO_MI = "Xiaomi"
    private const val MANUFACTURER_HUAWEI = "HUAWEI"



    fun getDeviceModel(): String {
        return OSUtils.getManufacturer() + " " + OSUtils.getModel()
    }


    @SuppressLint("HardwareIds", "MissingPermission")
    fun getPhoneNumber(): String? {
        val context = App.appContext
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
        return telephonyManager?.line1Number
    }

    /**
     * 拨打电话
     */
    @RequiresPermission(Manifest.permission.CALL_PHONE)
    fun openCall(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
        }
    }

    /**
     * 打开新信息界面
     */
    fun openSms(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phoneNumber"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
        }
    }



    private fun isInstallApp(context: Context, packName: String): Boolean {
        val packageManager: PackageManager = context.getPackageManager() // 获取packagemanager
        val pinfo: List<PackageInfo> = packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName.toLowerCase(Locale.ENGLISH)
                if (pn == packName) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 将字符串转为MD5
     */
    fun md5(data: String?): ByteArray {
        if (data.isNullOrEmpty()) {
            return "".toByteArray()
        }
        return try {
            val digest = MessageDigest.getInstance("MD5")
            digest.digest(data.toByteArray())
        } catch (e: Exception) {
            "".toByteArray()
        }
    }

    /**
     * 将字符串转为MD5
     */
    fun md5Hex(data: String?): String {
        return StringBuilder().apply {
            md5(data).forEach {
                val hex = Integer.toHexString(it.toInt() and 0xFF)
                append(if (hex.length == 1) "0$hex" else hex)
            }
        }.toString()
    }




    fun getSystemProperty(propName: String): String? {
        var classType: Class<*>? = null
        var propNameInfo: String? = null
        try {
            classType = Class.forName("android.os.SystemProperties")
            val getMethod = classType.getDeclaredMethod("get", *arrayOf<Class<*>>(String::class.java))
            propNameInfo = getMethod.invoke(classType, *arrayOf<Any>(propName)) as String
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return propNameInfo
    }

    fun getManufacturerVersionName(): String {
        if (OSUtils.getManufacturer().isNullOrEmpty()) {
            return ""
        }

        if (TextUtils.equals(OSUtils.getManufacturer(), MANUFACTURER_XIAO_MI)) {
            val miuiUiVersionName = "ro.miui.ui.version.name"
            return getSystemProperty(miuiUiVersionName).orEmpty()
        }

        if (TextUtils.equals(OSUtils.getManufacturer(), MANUFACTURER_HUAWEI)) {
            val emuiversionName = "ro.build.version.emui"
            return getSystemProperty(emuiversionName).orEmpty()
        }

        return ""

    }

}