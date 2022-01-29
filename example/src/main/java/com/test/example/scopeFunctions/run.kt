package com.test.example.scopeFunctions

/*
run
https://play.kotlinlang.org/byExample/06_scope_functions/02_run

与let类似，run是标准库中的另一个作用域函数。基本上，它做同样的事情:执行一个代码块并返回其结果。
不同的是，在内部运行对象是通过this访问的。当您想要调用对象的方法而不是将其作为参数传递时，这是很有用的。
 */

fun getNullableLength(ns: String?) {
    println("for \"$ns\":")
    val resutl = ns?.run {
        println("\tis empty? " + isEmpty())
        println("\tlength = $length")
        length
    }//返回最后一行

    println("resutl = $resutl")
}

fun main(){

    getNullableLength(null)
    getNullableLength("")
    getNullableLength("some string with Kotlin")

    //for "null":
    //resutl = null
    //for "":
    //	is empty? true
    //	length = 0
    //resutl = 0
    //for "some string with Kotlin":
    //	is empty? false
    //	length = 23
    //resutl = 23

}