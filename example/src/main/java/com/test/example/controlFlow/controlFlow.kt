package com.test.example.controlFlow

/*
https://play.kotlinlang.org/byExample/02_control_flow/01_When
 */

fun main() {

    cases("Hello")
    cases(1)
    cases(0L)
    cases(MyClass())
    cases("hello")
    //unknown
    //one
    //Long
    //not a string
    //Greeting
}

fun cases(obj: Any) {
    when (obj) {
        1 -> println("one")
        "hello" -> println("Greeting")
        is Long -> println("Long")
        !is String -> println("not a string")
        else -> println("unknown")
    }
}

class MyClass