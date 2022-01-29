package com.test.learnkotlin

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull


/*
异步流
https://www.kotlincn.net/docs/reference/coroutines/flow.html
 */

fun simple(): List<Int> = listOf(1, 2, 3)

//fun main() {
//    simple().forEach { value -> println(value) }
//    println("-------------------------------------------------------------------------------------")
//    simple2().forEach { value -> println(value) }
//    //1
//    //2
//    //3
//    //-------------------------------------------------------------------------------------
//    //1
//    //2
//    //3
//}


fun main() = runBlocking<Unit> {
    // 启动并发的协程以验证主线程并未阻塞
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(100)
        }
    }

    simple3().collect { value ->
        println(value)
    }
    //这段代码在不阻塞主线程的情况下每等待 100 毫秒打印一个数字。在主线程中运行一个单独的协程每 100 毫秒打印一次 “I'm not blocked” 已经经过了验证。

    //I'm not blocked 1
    //1
    //I'm not blocked 2
    //2
    //I'm not blocked 3
    //3

    /*
        注意使用 Flow 的代码与先前示例的下述区别：

        名为 flow 的 Flow 类型构建器函数。
        flow { ... } 构建块中的代码可以挂起。
        函数 simple 不再标有 suspend 修饰符。
        流使用 emit 函数 发射 值。
        流使用 collect 函数 收集 值。

     */

    println("--------------------------------------------------------------------------------------")


    //流是冷的
    //Flow 是一种类似于序列的冷流 — 这段 flow 构建器中的代码直到流被收集的时候才运行。这在以下的示例中非常明显：
    println("Calling simple function...")
    val flow = simple3()
    println("Calling collect...")
    flow.collect { value -> println(value) }
    println("Calling collect again...")
    flow.collect { value -> println(value) }
    //Calling simple function...
    //Calling collect...
    //Flow started
    //1
    //2
    //3
    //Calling collect again...
    //Flow started
    //1
    //2
    //3

    println("--------------------------------------------------------------------------------------")

    /*
        流取消基础
        流采用与协程同样的协作取消。像往常一样，流的收集可以在当流在一个可取消的挂起函数（例如 delay）中挂起的时候取消。
        以下示例展示了当 withTimeoutOrNull 块中代码在运行的时候流是如何在超时的情况下取消并停止执行其代码的：
     */
    withTimeoutOrNull(250) { // 在 250 毫秒后超时
        simple3().collect { value -> println(value) }
    }
    println("Done")
    //Flow started
    //1
    //2

    /*
        流构建器
        先前示例中的 flow { ... } 构建器是最基础的一个。还有其他构建器使流的声明更简单：

        flowOf 构建器定义了一个发射固定值集的流。
        使用 .asFlow() 扩展函数，可以将各种集合与序列转换为流。
        因此，从流中打印从 1 到 3 的数字的示例可以写成：

     */

    println("--------------------------------------------------------------------------------------")


    // 将一个整数区间转化为流
    (1..3).asFlow().collect { value -> println(value) }


    println("--------------------------------------------------------------------------------------")


    /*
        过渡流操作符
        可以使用操作符转换流，就像使用集合与序列一样。 过渡操作符应用于上游流，并返回下游流。 这些操作符也是冷操作符，就像流一样。这类操作符本身不是挂起函数。它运行的速度很快，返回新的转换流的定义。

        基础的操作符拥有相似的名字，比如 map 与 filter。 流与序列的主要区别在于这些操作符中的代码可以调用挂起函数。

        举例来说，一个请求中的流可以使用 map 操作符映射出结果，即使执行一个长时间的请求操作也可以使用挂起函数来实现：

     */
    (1..3).asFlow() // 一个请求流
        .map { request -> performRequest(request) }
        .collect { response -> println(response) }
    //response 1
    //response 2
    //response 3

    println("--------------------------------------------------------------------------------------")

    /*
        转换操作符
        在流转换操作符中，最通用的一种称为 transform。它可以用来模仿简单的转换，例如 map 与 filter，以及实施更复杂的转换。 使用 transform 操作符，我们可以 发射 任意值任意次。

        比如说，使用 transform 我们可以在执行长时间运行的异步请求之前发射一个字符串并跟踪这个响应：
     */
    (1..3).asFlow() // 一个请求流
        .transform { request ->
            emit("Making request $request")
            emit(performRequest(request))
        }
        .collect { response -> println(response) }
    //Making request 1
    //response 1
    //Making request 2
    //response 2
    //Making request 3
    //response 3

    println("--------------------------------------------------------------------------------------")

    /*
        限长操作符
        限长过渡操作符（例如 take）在流触及相应限制的时候会将它的执行取消。协程中的取消操作总是通过抛出异常来执行，
        这样所有的资源管理函数（如 try {...} finally {...} 块）会在取消的情况下正常运行：
     */
    numbers()
        .take(2) // 只获取前两个
        .collect { value -> println(value) }

    println("--------------------------------------------------------------------------------------")
    //1
    //2
    //Finally in numbers

    /*
        流是连续的
        流的每次单独收集都是按顺序执行的，除非进行特殊操作的操作符使用多个流。该收集过程直接在协程中运行，该协程调用末端操作符。
         默认情况下不启动新协程。 从上游到下游每个过渡操作符都会处理每个发射出的值然后再交给末端操作符。

     */

    println("--------------------------------------------------------------------------------------")
    (1..5).asFlow()
        .filter {
            println("Filter $it")
            it % 2 == 0
        }
        .map {
            println("Map $it")
            "string $it"
        }.collect {
            println("Collect $it")
        }
    //Filter 1
    //Filter 2
    //Map 2
    //Collect string 2
    //Filter 3
    //Filter 4
    //Map 4
    //Collect string 4
    //Filter 5

}

fun numbers(): Flow<Int> = flow {
    try {
        emit(1)
        emit(2)
        println("This line will not execute")
        emit(3)
    } finally {
        println("Finally in numbers")
    }
}


suspend fun performRequest(request: Int): String {
    delay(1000) // 模仿长时间运行的异步工作
    return "response $request"
}


//序列
//如果使用一些消耗 CPU 资源的阻塞代码计算数字（每次计算需要 100 毫秒）那么我们可以使用 Sequence 来表示数字：
fun simple2(): Sequence<Int> = sequence {
    for (i in 1..3) {
        Thread.sleep(100)// 假装我们正在计算
        yield(i)//产生下一个值
    }
}
//流
//使用 List 结果类型，意味着我们只能一次返回所有值。 为了表示异步计算的值流（stream），我们可以使用 Flow 类型（正如同步计算值会使用 Sequence 类型）：

fun simple3(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(100) // 假装我们在这里做了一些有用的事情
        emit(i) // 发送下一个值
    }
}