package com.test.learnkotlin
/*
可见性修饰符
https://www.kotlincn.net/docs/reference/visibility-modifiers.html

Visibility modifiers
https://kotlinlang.org/docs/visibility-modifiers.html#class-members


如果你不指定任何可见性修饰符，默认为 public，这意味着你的声明将随处可见；
如果你声明为 private，它只会在声明它的文件内可见；
如果你声明为 internal，它会在相同模块内随处可见；
protected 不适用于顶层声明。


 */

open class Outer {
    private val a = 1
    protected open val b = 2
    internal open val c1 = 3
    internal val c2 = 3
    val d = 4

    protected class Nested {
        public val e: Int = 5
    }
}

class Subclass : Outer() {
    // a c2 不可见
    override val b = 5
    override val c1 = 7

}

//这里的构造函数是私有的。默认情况下，所有构造函数都是 public，这实际上等于类可见的地方它就可见（即 一个 internal 类的构造函数只能在相同模块内可见).
class Unrelated private constructor(var outer: Outer) {

}


