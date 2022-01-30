package com.test.learnkotlin.base

import com.test.learnkotlin.http.HttpManager

open class BaseModel : IModel {
    override fun <T> createService(service: Class<T>): T {
        return HttpManager.getInstance().create(service)
    }
}