package com.test.example.scopeFunctions

/*
with    https://play.kotlinlang.org/byExample/06_scope_functions/03_with
With是一个非扩展函数，可以简洁地访问其参数的成员:在引用其成员时，可以省略实例名。
 */

fun main() {

    val personal = Personal("test", 20)
    val withResult = with(personal) {
        println("name = $name")
        println("age = $age")
        21
        22
    }

    println("withResult = $withResult")
    //name = test
    //age = 20
    //withResult = 22
}

class Personal(var name: String, var age: Int)