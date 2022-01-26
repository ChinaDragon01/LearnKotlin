package com.test.learnkotlin

import com.test.learnkotlin.bean.Person

/*
    官网文档-中文版：https://www.kotlincn.net/docs/reference/basic-syntax.html
    官网文档-英文版：https://kotlinlang.org/docs/basic-syntax.html#variables
 */

//fun main(){
//    println("hello world !")
//}

fun main(args: Array<String>) {
    println(args.contentToString())
    val person = Person("test")
    println(person.toString())

    var x = 5 // 自动推断出 `Int` 类型
    x += 1

    val rectangle = Rectangle(5.0, 2.0)
    println("The perimeter is ${rectangle.perimeter}")

    var a1 = 1
// simple name in template:
    val s1 = "a1 is $a1"
    println("s1：$s1")

    a1 = 2
// arbitrary expression in template:
    val s2 = "${s1.replace("is", "was")}, but now is $a1"
    println("s2：$s2")
    //s1：a1 is 1
    //s2：a1 was 1, but now is 2

    val items = listOf("apple", "banana", "kiwifruit")
    for (item in items) {
        println(item)
    }

    //apple
    //banana
    //kiwifruit

    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }

    //item at 0 is apple
    //item at 1 is banana
    //item at 2 is kiwifruit

    var index = 0
    while (index < items.size) {
        println("item at $index is ${items[index]}")
        index++
    }
    //item at 0 is apple
    //item at 1 is banana
    //item at 2 is kiwifruit


    val describeText = describe("Hello")
    println("describeText = $describeText")
    //describeText = Greeting


    val x1 = 10
    val y1 = 9
    if (x1 in 1..y1 + 1) {
        println("fits in range")
    }
    //fits in range


    val list = listOf("a", "b", "c")

    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }

    if (list.size !in list.indices) {
        println("list size is out of valid list indices range, too")
    }

    for (x in 1..5) {
        print(x)
    }
    println()
    for (x in 1..10 step 2) {
        print(x)
    }
    println()
    for (x in 9 downTo 0 step 3) {
        print(x)
    }

    //-1 is out of range
    //list size is out of valid list indices range, too
    //12345
    //13579
    //9630

    println()
    for (item in items) {
        println(item)
    }
    //apple
    //banana
    //kiwifruit
    when {
        "orange" in items -> println("juicy")
        "apple" in items -> println("apple is fine too")
    }
    //apple is fine too


    val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
    fruits
        .filter { it.startsWith("a") }// 筛选 以 a 字母开头的元素
        .sortedBy { it }//升序排序
        .map { it.uppercase() }//转大写
        .forEach { println(it) }

    //APPLE
    //AVOCADO

}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sum2(a: Int, b: Int) = a + b

fun printSum(a: Int, b: Int): Unit {
    println("sum of $a and $b is ${a + b}")
}

fun printSum2(a: Int, b: Int) {
    println("sum of $a and $b is ${a + b}")
}


val a: Int = 1  // 立即赋值
val b = 2   // 自动推断出 `Int` 类型

//val c1: Int // 提示错误信息：必须初始化
val c2: Int = 0  // 初始化
//c2 = 3       // 提示错误信息：Might be const


val PI = 3.14
var x = 0
fun incrementX() {
    x += 1
}

open class Shape {

}

class Rectangle(var height: Double, var length: Double) : Shape() {
    var perimeter = (height + length) * 2
}

fun maxOf(a: Int, b: Int): Int {
    if (a > b) {
        return a
    } else {
        return b
    }
}

fun maxOf2(a: Int, b: Int) = if (a > b) a else b

fun describe(obj: Any): String =
    when (obj) {
        1 -> "One"
        "Hello" -> "Greeting"
        is Long -> "Long"
        !is String -> "Not a string"
        else -> "Unknown"
    }

fun parseInt(str: String): Int? {
    return  if (!str.isNotEmpty() && str.equals("1")) 1 else 0
}

//使用返回可空值的函数:
fun printProduct(arg1: String, arg2: String) {
    val x = parseInt(arg1)
    val y = parseInt(arg2)

    // 直接使用 `x * y` 会导致编译错误，因为它们可能为 null
    if (x != null && y != null) {
        // 在空检测后，x 与 y 会自动转换为非空值（non-nullable）
        println(x * y)
    } else {
        println("'$arg1' or '$arg2' is not a number")
    }

    //或者
    if (x == null) {
        println("Wrong number format in arg1: '$arg1'")
        return
    }
    if (y == null) {
        println("Wrong number format in arg2: '$arg2'")
        return
    }

// 在空检测后，x 与 y 会自动转换为非空值
    println(x * y)
}

fun getStringLength1(obj: Any): Int? {
    if (obj is String) {
        // `obj` 在该条件分支内自动转换成 `String`
        return obj.length
    }

    // 在离开类型检测分支后，`obj` 仍然是 `Any` 类型
    return null
}

//或者
fun getStringLength2(obj: Any): Int? {
    if (obj !is String) return null

    // `obj` 在这一分支自动转换为 `String`
    return obj.length
}

//甚至
fun getStringLength3(obj: Any): Int? {
    // `obj` 在 `&&` 右边自动转换成 `String` 类型
    if (obj is String && obj.length > 0) {
        return obj.length
    }

    return null
}
