package com.test.learnkotlin

/*
函数式（SAM）接口      https://www.kotlincn.net/docs/reference/fun-interfaces.html

Functional (SAM) interfaces     https://kotlinlang.org/docs/fun-interfaces.html
 */
fun main() {

}


fun interface IntPredicate {
    fun accept(i: Int): Boolean
}

//不使用 SAM 转换
val isEven1 = object : IntPredicate {
    override fun accept(i: Int): Boolean {
        return i % 2 == 0
    }
}

//使用 SAM 转换
val isEven2 = IntPredicate { it % 2 == 0 }
