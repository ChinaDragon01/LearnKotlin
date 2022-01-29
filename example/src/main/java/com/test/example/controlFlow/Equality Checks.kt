package com.test.example.controlFlow

/*
Equality Checks
https://play.kotlinlang.org/byExample/02_control_flow/04_Equality%20Checks

揭秘 Kotlin 中的 == 和 ===
https://juejin.cn/post/6984606151845363743


 */

fun main() {
    val authors = setOf("Shakespeare", "Hemingway", "Twain")
    val writers = setOf("Twain", "Shakespeare", "Hemingway")

    println(authors == writers)
    println(authors === writers)
    //true
    //false
}