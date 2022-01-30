package com.test.learnkotlin.http.exception

import android.net.ParseException
import android.nfc.FormatException
import com.google.gson.JsonParseException
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.net.MalformedURLException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertPathValidatorException
import java.util.concurrent.CancellationException
import javax.net.ssl.SSLHandshakeException

/**
 * 网络请求异常处理
 *
 * @author liyunlong
 * @since 2020/5/25 16:10
 */
object ExceptionEngine {

    /**
     * 判断Token是否校验失败
     */
    fun isTokenCheckFailed(code: Int): Boolean {
        return code in listOf(10000401)
    }

    /**
     * 处理Token校验逻辑
     */
//    fun handleTokenCheck(entity: ResponseEntity<*>) {
//        AppExecutors.executeOnMainThread {
//            if (!entity.message.isNullOrEmpty()) {
//                ToastUtil.showToast(entity.message)
//            }
//            LoginHelper.logout(Tool.getContext(), needLogin = true, finishSelf = false)
//        }
//    }

    /**
     * 处理异常
     */
    fun handleException(e: Throwable): HttpException {
        return when (e) {
            is HttpException -> e
            is retrofit2.HttpException -> HttpException(e)
            else -> HttpException(getExceptionCode(e), e)
        }
    }

    private fun getExceptionCode(e: Throwable): Int {
        return if (e is NetErrorException || e is SocketException) {
            ExceptionCode.NETWORD_ERROR
        } else if (e is SocketTimeoutException) {
            ExceptionCode.SOCKET_TIMEOUT
        } else if (e is UnknownHostException) {
            ExceptionCode.UNKNOWN_HOST
        } else if (e is UnsupportedEncodingException) {
            ExceptionCode.UNSUPPORTED_ENCODING
        } else if (e is MalformedURLException) {
            ExceptionCode.MALFORMED_URL
        } else if (e is SSLHandshakeException) {
            ExceptionCode.SSL_ERROR
        } else if (e is CertPathValidatorException) {
            ExceptionCode.SSL_NOT_FOUND
        } else if (e is ClassCastException) {
            ExceptionCode.CLASS_CAST
        } else if (e is JsonParseException || e is JSONException) {
            ExceptionCode.JSON_PARSE
        } else if (e is FormatException) {
            ExceptionCode.FORMAT_ERROR
        } else if (e is NullPointerException) {
            ExceptionCode.DATA_NULL
        } else if (e is ParseException) {
            ExceptionCode.DATE_PARSE
        } else if (e is TokenCheckException) {
            ExceptionCode.TOKEN_CHECK
        } else if (e is CancellationException) {
            ExceptionCode.JOB_CANCELLED
        } else {
            ExceptionCode.UNKNOWN
        }
    }

}