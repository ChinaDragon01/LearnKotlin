package com.test.learnkotlin.http.converter

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonToken
import com.test.learnkotlin.http.ResponseEntity
import com.test.learnkotlin.http.exception.ExceptionEngine
import com.test.learnkotlin.http.exception.TokenCheckException
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.internal.closeQuietly
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets

/**
 * 使用[Gson]的Json[Converter.Factory]
 *
 */
class GsonConverterFactory private constructor(private val gson: Gson) : Converter.Factory() {

    override fun requestBodyConverter(type: Type,
                                      parameterAnnotations: Array<Annotation>,
                                      methodAnnotations: Array<Annotation>,
                                      retrofit: Retrofit): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type)) as TypeAdapter<*>
        return GsonRequestBodyConverter(gson, adapter)
    }

    override fun responseBodyConverter(type: Type,
                                       annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, *> {
        val adapter = gson.getAdapter(TypeToken.get(type)) as TypeAdapter<*>
        return GsonResponseBodyConverter(gson, adapter)
    }

    private class GsonRequestBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<T, RequestBody> {

        @Throws(IOException::class)
        override fun convert(value: T): RequestBody {
            val buffer = Buffer()
            val writer: Writer = OutputStreamWriter(buffer.outputStream(), StandardCharsets.UTF_8)
            val jsonWriter = gson.newJsonWriter(writer)
            adapter.write(jsonWriter, value)
            jsonWriter.closeQuietly()
            return buffer.readByteString().toRequestBody(MEDIA_TYPE)
        }

        companion object {
            private val MEDIA_TYPE: MediaType = "application/json; charset=UTF-8".toMediaType()
        }

    }

    private class GsonResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): T {
            val jsonReader = gson.newJsonReader(value.charStream())
            return value.use {
                val result = adapter.read(jsonReader)
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    throw JsonIOException("JSON document was not fully consumed.")
                }
                jsonReader.closeQuietly()
                if (result is ResponseEntity<*> && ExceptionEngine.isTokenCheckFailed(result.code)) {
                    ExceptionEngine.handleTokenCheck(result)
                    throw TokenCheckException(result.message, result)
                }
                result
            }
        }
    }

    companion object {
        fun create(gson: Gson? = Gson()): GsonConverterFactory {
            if (gson == null) {
                throw NullPointerException("The gson is null.")
            }
            return GsonConverterFactory(gson)
        }
    }

}