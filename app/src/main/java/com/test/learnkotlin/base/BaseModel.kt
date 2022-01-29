package com.test.learnkotlin.base

open class BaseModel : IModel {
    override fun <T> createService(service: Class<T>): T {
        //  return HttpManager.getInstance().create(service)
        TODO("Not yet implemented")
    }
}