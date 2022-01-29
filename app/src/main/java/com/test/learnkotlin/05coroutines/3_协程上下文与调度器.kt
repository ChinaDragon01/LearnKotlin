package com.test.learnkotlin

import kotlinx.coroutines.*

/*
协程上下文与调度器
https://www.kotlincn.net/docs/reference/coroutines/coroutine-context-and-dispatchers.html

协程上下文与调度器
协程总是运行在一些以 CoroutineContext 类型为代表的上下文中，它们被定义在了 Kotlin 的标准库里。

协程上下文是各种不同元素的集合。其中主元素是协程中的 Job， 我们在前面的文档中见过它以及它的调度器，而本文将对它进行介绍。

调度器与线程
协程上下文包含一个 协程调度器 （参见 CoroutineDispatcher）它确定了相关的协程在哪个线程或哪些线程上执行。协程调度器可以将协程限制在一个特定的线程执行，或将它分派到一个线程池，亦或是让它不受限地运行。

所有的协程构建器诸如 launch 和 async 接收一个可选的 CoroutineContext 参数，它可以被用来显式的为一个新协程或其它上下文元素指定一个调度器。

 */
//
//fun main() = runBlocking<Unit> {
////    launch { // 运行在父协程的上下文中，即 runBlocking 主协程
////        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
////    }
////    launch(Dispatchers.Unconfined) { // 不受限的——将工作在主线程中
////        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
////    }
////    launch(Dispatchers.Default) { // 将会获取默认调度器
////        println("Default               : I'm working in thread ${Thread.currentThread().name}")
////    }
////    launch(newSingleThreadContext("MyOwnThread")) { // 将使它获得一个新的线程
////        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
////    }
//
//    //Unconfined            : I'm working in thread main
//    //Default               : I'm working in thread DefaultDispatcher-worker-1
//    //main runBlocking      : I'm working in thread main
//    //newSingleThreadContext: I'm working in thread MyOwnThread
//
//
//    //非受限调度器 vs 受限调度器
//    launch(Dispatchers.Unconfined) { // 非受限的——将和主线程一起工作
//        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
//        delay(500)
//        println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
//    }
//    launch { // 父协程的上下文，主 runBlocking 协程
//        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
//        delay(1000)
//        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
//    }
//    //Unconfined      : I'm working in thread main
//    //main runBlocking: I'm working in thread main
//    //Unconfined      : After delay in thread kotlinx.coroutines.DefaultExecutor
//    //main runBlocking: After delay in thread main
//
//    /*
//        所以，该协程的上下文继承自 runBlocking {...} 协程并在 main 线程中运行，当 delay 函数调用的时候，非受限的那个协程在默认的执行者线程中恢复执行。
//
//        非受限的调度器是一种高级机制，可以在某些极端情况下提供帮助而不需要调度协程以便稍后执行或产生不希望的副作用，
//        因为某些操作必须立即在协程中执行。 非受限调度器不应该在通常的代码中使用。
//     */
//}

/*
非受限调度器 vs 受限调度器
Dispatchers.Unconfined 协程调度器在调用它的线程启动了一个协程，但它仅仅只是运行到第一个挂起点。挂起后，它恢复线程中的协程，而这完全由被调用的挂起函数来决定。
非受限的调度器非常适用于执行不消耗 CPU 时间的任务，以及不更新局限于特定线程的任何共享数据（如UI）的协程。

另一方面，该调度器默认继承了外部的 CoroutineScope。 runBlocking 协程的默认调度器，特别是， 当它被限制在了调用者线程时，继承自它将会有效地限制协程在该线程运行并且具有可预测的 FIFO 调度。

 */

/*
协程作用域
让我们将关于上下文，子协程以及作业的知识综合在一起。假设我们的应用程序拥有一个具有生命周期的对象，但这个对象并不是一个协程。
举例来说，我们编写了一个 Android 应用程序并在 Android 的 activity 上下文中启动了一组协程来使用异步操作拉取并更新数据以及执行动画等等。
所有这些协程必须在这个 activity 销毁的时候取消以避免内存泄漏。当然，我们也可以手动操作上下文与作业，以结合 activity 的生命周期与它的协程，
但是 kotlinx.coroutines 提供了一个封装：CoroutineScope 的抽象。 你应该已经熟悉了协程作用域，因为所有的协程构建器都声明为在它之上的扩展。

我们通过创建一个 CoroutineScope 实例来管理协程的生命周期，并使它与 activity 的生命周期相关联。
CoroutineScope 可以通过 CoroutineScope() 创建或者通过MainScope() 工厂函数。前者创建了一个通用作用域，而后者为使用 Dispatchers.Main 作为默认调度器的 UI 应用程序 创建作用域：


 */

fun main() = runBlocking{
    //在 main 函数中我们创建 activity，调用测试函数 doSomething，并且在 500 毫秒后销毁这个 activity。 这取消了从 doSomething 启动的所有协程。
// 我们可以观察到这些是由于在销毁之后， 即使我们再等一会儿，activity 也不再打印消息。
    val activity = Activity()
    activity.doSomething() // 运行测试函数
    println("Launched coroutines")
    delay(500L) // 延迟半秒钟
    println("Destroying activity!")
    activity.destroy() // 取消所有的协程
    delay(1000) // 为了在视觉上确认它们没有工作
    //Launched coroutines
    //Coroutine 0 is done
    //Coroutine 1 is done
    //Destroying activity!

    //你可以看到，只有前两个协程打印了消息，而另一个协程在 Activity.destroy() 中单次调用了 job.cancel()。
    //
    //注意，Android 在所有具有生命周期的实体中都对协程作用域提供了一等的支持。 请查看相关文档。https://developer.android.com/topic/libraries/architecture/coroutines#lifecyclescope
}

class Activity {
    private val mainScope = MainScope()

    fun destroy() {
        mainScope.cancel()
    }
// 继续运行……

    // 在 Activity 类中
    fun doSomething() {
        // 在示例中启动了 10 个协程，且每个都工作了不同的时长
        repeat(10) { i ->
            mainScope.launch(Dispatchers.IO) {//注意这里需要添加 Dispatchers , 否则编译报错
                delay((i + 1) * 200L) // 延迟 200 毫秒、400 毫秒、600 毫秒等等不同的时间
                println("Coroutine $i is done")
            }
        }
    }
} // Activity 类结束
