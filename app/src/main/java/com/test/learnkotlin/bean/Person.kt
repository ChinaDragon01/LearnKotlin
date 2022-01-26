package com.test.learnkotlin.bean

data class Person(var name: String, var id: Int = 1000, val age: Int = 20, var address: String? = null) {

    override fun toString(): String {
        return "Person(name='$name', id=$id, age=$age, address=$address)"
    }
}