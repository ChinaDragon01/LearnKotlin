package com.test.learnkotlin

/*
    密封类     https://www.kotlincn.net/docs/reference/sealed-classes.html

    sealed-classes      https://kotlinlang.org/docs/sealed-classes.html#sealed-classes-and-when-expression
 */
fun main() {
//    log(IOError()) // 报错 it is protected
    log(CustomError())
}

sealed class Expr

sealed class IOError() : Expr()

open class CustomError() : Expr()

data class Const(val number: Double) : Expr()

data class Sum(val e1: Error, val e2: Error) : Expr()

object NotANumber : Expr()

fun log(e: Expr) = when (e) {
    is IOError -> println("Expr IOError ")
    is CustomError -> println("Expr CustomError ")
    is Const -> println("Expr Const ")
    is Sum -> println("Expr Sum ")
    is NotANumber -> println("Expr NotANumber ")
    else -> ""
}



