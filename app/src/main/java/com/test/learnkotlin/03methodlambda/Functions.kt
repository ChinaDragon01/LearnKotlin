package com.test.learnkotlin

import java.util.Collection

/*
函数  https://www.kotlincn.net/docs/reference/functions.html

Functions       https://kotlinlang.org/docs/functions.html
 */

fun main() {

    val b = B()
    println(" b.foo() = ${b.foo()}")
    println(" b.foo(5) = ${b.foo(5)}")
    // b.foo() = 10
    // b.foo(5) = 5

    val a = asList(1, 2, 3)
    val list = asList(-1, 0, "kotlim", 4)

    // 用中缀表示法调用该函数
    1 shl 2

// 等同于这样
    1.shl(2)


    //高阶函数
    val items = listOf(1, 2, 3, 4, 5)

// Lambdas 表达式是花括号括起来的代码块。
    items.fold(0, {
        // 如果一个 lambda 表达式有参数，前面是参数，后跟“->”
            acc: Int, i: Int ->
        print("acc = $acc, i = $i, ")
        val result = acc + i
        println("result = $result")
        // lambda 表达式中的最后一个表达式是返回值：
        result
    })

// lambda 表达式的参数类型是可选的，如果能够推断出来的话：
    val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })

// 函数引用也可以用于高阶函数调用：
    val product = items.fold(1, Int::times)



    /*
        尾递归函数
        Kotlin 支持一种称为尾递归的函数式编程风格。 这允许一些通常用循环写的算法改用递归函数来写，而无堆栈溢出的风险。
        当一个函数用 tailrec 修饰符标记并满足所需的形式时，编译器会优化该递归，留下一个快速而高效的基于循环的版本：

     */



    for (i in 1 until 10) {       // i in [1, 10), 10被排除
        print(i)
    }

    for (i in 1 .. 4){ // 1<= i <= 4

    }

}



//默认参数
//函数参数可以有默认值，当省略相应的参数时使用默认值。与其他语言相比，这可以减少重载数量：
fun read(
    b: Array<Byte>,
    off: Int = 0,
    len: Int = b.size,
) { /*……*/
}

//覆盖方法总是使用与基类型方法相同的默认参数值。 当覆盖一个带有默认参数值的方法时，必须从签名中省略默认参数值：

open class A {
    open fun foo(i: Int = 10) = i
}

class B : A() {
    override fun foo(i: Int): Int {
        return i
    }
}

//可变数量的参数（Varargs）
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) { // ts 是数组
        result.add(t)
    }

    return result
}

/*
中缀表示法

标有 infix 关键字的函数也可以使用中缀表示法（忽略该调用的点与圆括号）调用。中缀函数必须满足以下要求：

它们必须是成员函数或扩展函数；
它们必须只有一个参数；



中缀函数调用的优先级低于算术操作符、类型转换以及 rangeTo 操作符。 以下表达式是等价的：

1 shl 2 + 3 等价于 1 shl (2 + 3)
0 until n * 2 等价于 0 until (n * 2)
xs union ys as Set<*> 等价于 xs union (ys as Set<*>)
另一方面，中缀函数调用的优先级高于布尔操作符 && 与 ||、is- 与 in- 检测以及其他一些操作符。这些表达式也是等价的：

a && b xor c 等价于 a && (b xor c)
a xor b in c 等价于 (a xor b) in c



 */
//infix fun Int.shl(bitCount:Int):Int

//泛型函数
//函数可以有泛型参数，通过在函数名前使用尖括号指定：
fun <T> singletonList(item: T): List<T> {
    val array = ArrayList<T>()
    array.add(item)
    return array
}



/*

高阶函数
高阶函数是将函数用作参数或返回值的函数。

一个不错的示例是集合的函数式风格的 fold， 它接受一个初始累积值与一个接合函数，并通过将当前累积值与每个集合元素连续接合起来代入累积值来构建返回值：
 */

fun <T, R> Collection<T>.fold(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}

/*
在上述代码中，参数 combine 具有函数类型 (R, T) -> R，因此 fold 接受一个函数作为参数， 该函数接受类型分别为 R 与 T 的两个参数并返回一个 R 类型的值。

在 for-循环内部调用该函数，然后将其返回值赋值给 accumulator。

为了调用 fold，需要传给它一个函数类型的实例作为参数，而在高阶函数调用处，（下文详述的）lambda 表达 式广泛用于此目的。

 */



/*
     尾递归函数
     Kotlin 支持一种称为尾递归的函数式编程风格。 这允许一些通常用循环写的算法改用递归函数来写，而无堆栈溢出的风险。
     当一个函数用 tailrec 修饰符标记并满足所需的形式时，编译器会优化该递归，留下一个快速而高效的基于循环的版本：

  */
val eps = 1E-10 // "good enough", could be 10^-15

tailrec fun findFixPoint(x: Double = 1.0): Double
        = if (Math.abs(x - Math.cos(x)) < eps) x else findFixPoint(Math.cos(x))

/*
这段代码计算余弦的不动点（fixpoint of cosine），这是一个数学常数。 它只是重复地从 1.0 开始调用 Math.cos，直到结果不再改变，
对于这里指定的 eps 精度会产生 0.7390851332151611 的结果。最终代码相当于这种更传统风格的代码：


val eps = 1E-10 // "good enough", could be 10^-15

private fun findFixPoint(): Double {
    var x = 1.0
    while (true) {
        val y = Math.cos(x)
        if (Math.abs(x - y) < eps) return x
        x = Math.cos(x)
    }
}

 */

// 要符合 tailrec 修饰符的条件的话，函数必须将其自身调用作为它执行的最后一个操作。
// 在递归调用后有更多代码时，不能使用尾递归，并且不能用在 try/catch/finally 块中。目前在 Kotlin for JVM 与 Kotlin/Native 中支持尾递归。