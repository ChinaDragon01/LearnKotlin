package com.test.learnkotlin
/*
内联类     https://www.kotlincn.net/docs/reference/inline-classes.html

Inline classes      https://kotlinlang.org/docs/inline-classes.html

有时候，业务逻辑需要围绕某种类型创建包装器。然而，由于额外的堆内存分配问题，它会引入运行时的性能开销。此外，如果被包装的类型是原生类型，性能的损失是很糟糕的，因为原生类型通常在运行时就进行了大量优化，然而他们的包装器却没有得到任何特殊的处理。

为了解决这类问题，Kotlin 引入了一种被称为 内联类 的特殊类，它通过在类的前面定义一个 inline 修饰符来声明：

inline class Password(val value: String)
内联类必须含有唯一的一个属性在主构造函数中初始化。在运行时，将使用这个唯一属性来表示内联类的实例（关于运行时的内部表达请参阅下文）：

// 不存在 'Password' 类的真实实例对象
// 在运行时，'securePassword' 仅仅包含 'String'
val securePassword = Password("Don't try this in production")
这就是内联类的主要特性，它灵感来源于 “inline” 这个名称：类的数据被 “内联”到该类使用的地方（类似于内联函数中的代码被内联到该函数调用的地方）。


-------------------------------------------

然而，内联类的成员也有一些限制：

内联类不能含有 init 代码块
内联类不能含有幕后字段
因此，内联类只能含有简单的计算属性（不能含有延迟初始化/委托属性）


 */

fun main() {
    val name = Name("kotlin")
    name.greet()
    println("name.length = ${name.length}")

    //hello kotlin
    //name.length = 6

}

inline class Name(val s: String) {
    //注意 局部变量 s 前面 val ，如果不加 val 则报错
    val length: Int
    get() = s.length

    fun greet(){
        println("hello $s")
    }

}

