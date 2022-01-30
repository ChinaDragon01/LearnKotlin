package com.test.learnkotlin.http.interceptor

import android.webkit.WebSettings
import com.test.learnkotlin.app.App
import com.test.learnkotlin.http.HttpConfig
import com.test.learnkotlin.http.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor : Interceptor {
    private val userAgent = WebSettings.getDefaultUserAgent(App.appContext)
    private val whitelistUrls = listOf(HttpUrl.LOGIN)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader("User-Agent", "$userAgent")
        HttpConfig.getHttpHeaders().map { (key, value) ->
            builder.addHeader(key, value)
        }

        if (whitelistUrls.contains(request.url.encodedPath)) {
            builder.removeHeader(HttpConfig.KEY_TOKEN)
        }


        return chain.proceed(builder.build())
    }
}