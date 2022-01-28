package com.test.example.functional

/*

Higher-Order Functions https://play.kotlinlang.org/byExample/04_functional/01_Higher-Order%20Functions

 */


fun main() {
    val sumResult = calculate(4, 5, ::sum)                          // 4
    val mulResult = calculate(4, 5) { a, b -> a * b }               // 5
    println("sumResult $sumResult, mulResult $mulResult")
    //sumResult 9, mulResult 20
}

fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {  // 1
    return operation(x, y)                                          // 2
}

fun sum(x: Int, y: Int) = x + y