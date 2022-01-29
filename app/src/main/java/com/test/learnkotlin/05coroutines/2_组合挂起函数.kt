package com.test.learnkotlin

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/*
组合挂起函数  https://www.kotlincn.net/docs/reference/coroutines/composing-suspending-functions.html
 */
//fun main() = runBlocking {
//
////    val time = measureTimeMillis {
////        val one = doSomethingUsefulOne()
////        val two = doSomethingUsefulTwo()
////        println("The answer is ${one + two}")
////    }
////    println("Completed in $time ms")//测量执行两个挂起函数所需要的总时间：
//    //The answer is 42
//    //Completed in 2047 ms
//
//
//    /*
//     使用 async 并发
//     如果 doSomethingUsefulOne 与 doSomethingUsefulTwo 之间没有依赖，并且我们想更快的得到结果，让它们进行 并发 吗？这就是 async 可以帮助我们的地方。
//
//     在概念上，async 就类似于 launch。它启动了一个单独的协程，这是一个轻量级的线程并与其它所有的协程一起并发的工作。不同之处在于 launch 返回一个 Job 并且不附带任何结果值，
//     而 async 返回一个 Deferred —— 一个轻量级的非阻塞 future， 这代表了一个将会在稍后提供结果的 promise。你可以使用 .await() 在一个延期的值上得到它的最终结果，
//     但是 Deferred 也是一个 Job，所以如果需要的话，你可以取消它。
//
//  */
////    val time = measureTimeMillis {
////        val one = async { doSomethingUsefulOne() }
////        val two = async { doSomethingUsefulTwo() }
////        println("The answer is ${one.await() + two.await()}")
////    }
////    println("Completed in $time ms")
//
//    //The answer is 42
//    //Completed in 1022 ms
//
//
//    /*
//        惰性启动的 async
//        可选的，async 可以通过将 start 参数设置为 CoroutineStart.LAZY 而变为惰性的。 在这个模式下，只有结果通过 await 获取的时候协程才会启动，
//        或者在 Job 的 start 函数调用的时候。运行下面的示例：
//
//     */
//
//    val time = measureTimeMillis {
//        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
//        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
//        // 执行一些计算
////        one.start() // 启动第一个
////        two.start() // 启动第二个
//        println("The answer is ${one.await() + two.await()}")
//    }
//    println("Completed in $time ms")
//    //The answer is 42
//    //Completed in 1023 ms
//
//    /*
//       注意，如果我们只是在 println 中调用 await，而没有在单独的协程中调用 start，这将会导致顺序行为，直到 await 启动该协程 执行并等待至它结束，
//       这并不是惰性的预期用例。 在计算一个值涉及挂起函数时，这个 async(start = CoroutineStart.LAZY) 的用例用于替代标准库中的 lazy 函数。
//
//       将上面的  one.start()  two.start() 注释掉，结果如下
//     */
//    //The answer is 42
//    //Completed in 2022 ms
//
//}

//fun main(){
//
//    //注意，这些 xxxAsync 函数不是 挂起 函数。它们可以在任何地方使用。 然而，它们总是在调用它们的代码中意味着异步（这里的意思是 并发 ）执行。
//    //
//    //下面的例子展示了它们在协程的外面是如何使用的：
//    val time = measureTimeMillis {
//        // 我们可以在协程外面启动异步执行
//        val one = somethingUsefulOneAsync()
//        val two = somethingUsefulTwoAsync()
//        // 但是等待结果必须调用其它的挂起或者阻塞
//        // 当我们等待结果的时候，这里我们使用 `runBlocking { …… }` 来阻塞主线程
//        runBlocking {
//            println("The answer is ${one.await() + two.await()}")
//        }
//    }
//    println("Completed in $time ms")
//    //The answer is 42
//    //Completed in 1100 ms
//
//    /*
//        这种带有异步函数的编程风格仅供参考，因为这在其它编程语言中是一种受欢迎的风格。在 Kotlin 的协程中使用这种风格是强烈不推荐的， 原因如下所述。
//        考虑一下如果 val one = somethingUsefulOneAsync() 这一行和 one.await() 表达式这里在代码中有逻辑错误， 并且程序抛出了异常以及程序在操作的过程中中止，
//        将会发生什么。 通常情况下，一个全局的异常处理者会捕获这个异常，将异常打印成日记并报告给开发者，但是反之该程序将会继续执行其它操作。
//        但是这里我们的 somethingUsefulOneAsync 仍然在后台执行， 尽管如此，启动它的那次操作也会被终止。这个程序将不会进行结构化并发，如下一小节所示。
//     */
//}

//fun main() = runBlocking{
//    val time = measureTimeMillis {
//        println("The answer is ${concurrentSum()}")
//    }
//    println("Completed in $time ms")
//    //The answer is 42
//    //Completed in 1019 ms
//}

fun main() = runBlocking<Unit> {//这里添加 Unit
    //取消始终通过协程的层次结构来进行传递：
    try {
        failedConcurrentSum()
    } catch(e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
    //Second child throws an exception
    //First child was cancelled
    //Computation failed with ArithmeticException
}

suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE) // 模拟一个长时间的运算
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // 假设我们在这里做了一些有用的事
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // 假设我们在这里也做了一些有用的事
    return 29
}

/*
async 风格的函数
我们可以定义异步风格的函数来 异步 的调用 doSomethingUsefulOne 和 doSomethingUsefulTwo 并使用 async 协程建造器并带有一个显式的 GlobalScope 引用。
我们给这样的函数的名称中加上“……Async”后缀来突出表明：事实上，它们只做异步计算并且需要使用延期的值来获得结果。

 */

// somethingUsefulOneAsync 函数的返回值类型是 Deferred<Int>
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

// somethingUsefulTwoAsync 函数的返回值类型是 Deferred<Int>
fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}


/*
    使用 async 的结构化并发
    让我们使用使用 async 的并发这一小节的例子并且提取出一个函数并发的调用 doSomethingUsefulOne 与 doSomethingUsefulTwo 并且返回它们两个的结果之和。
    由于 async 被定义为了 CoroutineScope 上的扩展，我们需要将它写在作用域内，并且这是 coroutineScope 函数所提供的：



    这种情况下，如果在 concurrentSum 函数内部发生了错误，并且它抛出了一个异常， 所有在作用域中启动的协程都会被取消。
 */

suspend fun concurrentSum():Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}