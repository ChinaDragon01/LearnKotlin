package com.test.learnkotlin.base

interface IModel {
    fun <T> createService(service: Class<T>): T
}