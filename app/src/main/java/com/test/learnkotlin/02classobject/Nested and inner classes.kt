package com.test.learnkotlin
/*
嵌套类与内部类     https://www.kotlincn.net/docs/reference/nested-classes.html

Nested and inner classes        https://kotlinlang.org/docs/nested-classes.html
 */

fun main() {
    val demo1 = Outer2.Nested().f00()

    val demo2 = Outer2().Inner().foo()

    println("demo1 = $demo1  , demo2 = $demo2")
    //demo1 = 2  , demo2 = 1

}

class Outer2 {
    private val bar: Int = 1

    class Nested {
        fun f00() = 2
    }

    //标记为 inner 的嵌套类能够访问其外部类的成员。内部类会带有一个对外部类的对象的引用：
    inner class Inner {
        fun foo() = bar
    }

}

