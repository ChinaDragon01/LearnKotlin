package com.test.example.scopeFunctions

/*
let
https://play.kotlinlang.org/byExample/06_scope_functions/01_let
The Kotlin standard library function let can be used for scoping and null-checks. When called on an object,
let executes the given block of code and returns the result of its last expression.
 The object is accessible inside the block by the reference it (by default) or a custom name.

 Kotlin标准库函数let可用于范围确定和空检查。当调用对象时，let执行给定的代码块并返回其最后一个表达式的结果。对象可以通过引用(默认情况下)或自定义名称在块中访问。

 */

fun main() {
    println("isEmpty = $isEmpty")
    printNonNull(null)
    printNonNull("my string")
    printIfBothNonNull("First","Second")
    //test
    //isEmpty = false
    //Printing "null":
    //Printing "my string":
    //	my string
    //
    //First : Second

}

val isEmpty = "test".let {
    println(it)               // 2
    it.isEmpty()
}

fun printNonNull(str:String?){
    println("Printing \"$str\":")
    str?.let {
        print("\t")
        println(it)
        println()
    }
}

fun printIfBothNonNull(strOne: String?, strTwo: String?) {
    strOne?.let { firstString ->
        strTwo?.let { secondString ->
            println("$firstString : $secondString")
            println()
        }
    }
}