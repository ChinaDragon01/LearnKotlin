package com.test.learnkotlin
/*
委托      https://www.kotlincn.net/docs/reference/delegation.html

Delegation  https://kotlinlang.org/docs/delegation.html

由委托实现
委托模式 已经证明是 实现继承 的一个很好的替代方式， 而 Kotlin 可以零样板代码地原生支持它。 Derived 类可以通过将其所有公有成员都委托给指定对象来实现一个接口 Base：

 */

fun main() {
    val baseImpl = BaseImpl(20)
    Derived(baseImpl).print()
    //20
    //Derived 的超类型列表中的 by-子句表示 b 将会在 Derived 中内部存储， 并且编译器将生成转发给 b 的所有 Base 的方法。
}

interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() {
        print(x)
    }
}

class Derived(b: Base):Base by b
