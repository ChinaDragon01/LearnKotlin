package com.test.learnkotlin.http

import android.annotation.SuppressLint
import android.util.ArrayMap
import com.test.learnkotlin.app.App
import com.test.learnkotlin.http.interceptor.HeadersInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpManager private constructor() {
    private var retrofit: Retrofit? = null
    private val context = App.appContext
    private val serviceMap = ArrayMap<String, Any?>()

    private object SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        val INSTANCE = HttpManager()
    }

    private fun getOkHttpClient(): OkHttpClient {
        var builder = OkHttpClient.Builder()
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(HeadersInterceptor())

        val debug = true

        if (debug) {
            var httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)
        }

        return builder.build()

    }

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(HttpUrl.BASE_URL_PRODUCT)
                .client(getOkHttpClient())
                .validateEagerly(true)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    /**
     * 更新baseUrl
     */
    fun updateBaseUrl(baseUrl: String) {
        serviceMap.clear()
        retrofit = getRetrofit().newBuilder()
            .baseUrl(baseUrl)
            .build()
    }

    /**
     * 创建ApiService
     */
    fun <T> create(service: Class<T>): T {
        val serviceKey = service.name

        @Suppress("UNCHECKED_CAST")
        var serviceValue = serviceMap[serviceKey] as T
        if (serviceValue == null) {
            serviceValue = getRetrofit().create(service)
            serviceMap[serviceKey] = serviceValue
        }
        return serviceValue
    }

    /**
     * 创建ApiService
     */
    fun <T> create(baseUrl: String, service: Class<T>): T {
        return getRetrofit().newBuilder()
            .baseUrl(baseUrl)
            .build()
            .create(service)
    }

    companion object {
        private const val TIME_OUT = 30L

        @JvmStatic
        fun getInstance(): HttpManager = SingletonHolder.INSTANCE
    }

}