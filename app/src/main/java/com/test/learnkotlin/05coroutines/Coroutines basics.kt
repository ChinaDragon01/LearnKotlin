package com.test.learnkotlin

import kotlinx.coroutines.*
import kotlin.concurrent.thread

/*

Coroutines basics   https://kotlinlang.org/docs/coroutines-basics.html

https://github.com/Kotlin/kotlinx.coroutines/blob/master/README.md#using-in-your-projects

https://developer.android.com/kotlin/coroutines


将 Kotlin 协程与生命周期感知型组件一起使用
https://developer.android.com/topic/libraries/architecture/coroutines#lifecyclescope

 */
//
//
//fun main(){
////    GlobalScope.launch { // 在后台启动一个新的协程并继续
////        delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
////        println("World!") // 在延迟后打印输出
////    }
//
////    println("Hello,") // 协程已在等待时主线程还在继续
////    Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
//
//    //Hello,
//    //World!
//
//
//    //---------------------------------------------------------------------
//
////    Thread(Runnable {
////
////        Thread.sleep(1000L)
////        println("World!")
////
////    }).start()
//
////    thread {
////        Thread.sleep(1000L)
//////        delay(1000L) //Suspend function 'delay' should be called only from a coroutine or another suspend function
////        // delay 是一个特殊的 挂起函数 ，它不会造成线程阻塞，但是会 挂起 协程，并且只能在协程中使用。
////
////        println("World!")
////    }
////
////    println("Hello,") // 协程已在等待时主线程还在继续
////    Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
//    //Hello,
//    //World!
//
//
//    //----------------------------------------------------------------------
//
//    //桥接阻塞与非阻塞的世界
//    //第一个示例在同一段代码中混用了 非阻塞的 delay(……) 与 阻塞的 Thread.sleep(……)。
//// 这容易让我们记混哪个是阻塞的、哪个是非阻塞的。 让我们显式使用 runBlocking 协程构建器来阻塞：
//
//    GlobalScope.launch {//在后台启动一个新的协程并继续
//        delay(1000L)
//        println("World!")
//
//    }
//    println("Hello,")
//    runBlocking {
//        delay(2000L)
//    }
//
//
//}


//使用 runBlocking 来包装 main 函数的执行：
//fun main() = runBlocking<Unit> {// 开始执行主协程
//   val job =  GlobalScope.launch { // 在后台启动一个新的协程并继续
//        delay(1000L)
//        println("World!")
//    }
//    println("Hello,") // 主协程在这里会立即执行
//    delay(2000L)      // 延迟 2 秒来保证 JVM 存活
//
//    //Hello,
//    //World!
//}


//等待一个作业
//fun main() = runBlocking{
//    val job = GlobalScope.launch {// 启动一个新协程并保持对这个作业的引用
//        delay(1000L)
//        println("World!")
//    }
//    println("Hello,")
//    job.join() // 等待直到子协程执行结束
//    //join 是挂起函数 ，
//}

/*
结构化的并发

协程的实际使用还有一些需要改进的地方。 当我们使用 GlobalScope.launch 时，我们会创建一个顶层协程。虽然它很轻量，但它运行时仍会消耗一些内存资源。
如果我们忘记保持对新启动的协程的引用，它还会继续运行。如果协程中的代码挂起了会怎么样（例如，我们错误地延迟了太长时间），
如果我们启动了太多的协程并导致内存不足会怎么样？ 必须手动保持对所有已启动协程的引用并 join 之很容易出错。

有一个更好的解决办法。我们可以在代码中使用结构化并发。 我们可以在执行操作所在的指定作用域内启动协程， 而不是像通常使用线程（线程总是全局的）那样在 GlobalScope 中启动。

在我们的示例中，我们使用 runBlocking 协程构建器将 main 函数转换为协程。 包括 runBlocking 在内的每个协程构建器都将 CoroutineScope 的实例添加到其代码块所在的作用域中。
我们可以在这个作用域中启动协程而无需显式 join 之，因为外部协程（示例中的 runBlocking）直到在其作用域中启动的所有协程都执行完毕后才会结束。因此，可以将我们的示例简化为：

 */

//fun main() = runBlocking {
//    launch {// 在 runBlocking 作用域中启动一个新协程
//        delay(1000L)
//        println("World!")
//    }
//
//    println("Hello,")
//}


/*
作用域构建器

除了由不同的构建器提供协程作用域之外，还可以使用 coroutineScope 构建器声明自己的作用域。它会创建一个协程作用域并且在所有已启动子协程执行完毕之前不会结束。

runBlocking 与 coroutineScope 可能看起来很类似，因为它们都会等待其协程体以及所有子协程结束。 主要区别在于，runBlocking 方法会阻塞当前线程来等待，
而 coroutineScope 只是挂起，会释放底层线程用于其他用途。 由于存在这点差异，runBlocking 是常规函数，而 coroutineScope 是挂起函数。

 */

//fun main() = runBlocking {
//    launch {
//        delay(200L)
//        println("Task from runBlocking")
//    }
//
//    coroutineScope { // 创建一个协程作用域
//        launch {
//            delay(500L)
//            println("Task from nested launch")
//        }
//
//        delay(100L)
//        println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
//    }
//
//    println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
//}

//Task from coroutine scope
//Task from runBlocking
//Task from nested launch
//Coroutine scope is over
//请注意，（当等待内嵌 launch 时）紧挨“Task from coroutine scope”消息之后， 就会执行并输出“Task from runBlocking”——尽管 coroutineScope 尚未结束。

/*
提取函数重构
我们来将 launch { …… } 内部的代码块提取到独立的函数中。当你对这段代码执行“提取函数”重构时，你会得到一个带有 suspend 修饰符的新函数。
这是你的第一个挂起函数。在协程内部可以像普通函数一样使用挂起函数， 不过其额外特性是，同样可以使用其他挂起函数（如本例中的 delay）来挂起协程的执行。

 */

//fun main() = runBlocking {
//    launch { doWorld() }
//    println("Hello,")
//    //Hello,
//    //world
//}
//
//suspend fun doWorld(){
//    delay(1000L)
//    println("world")
//}

//协程很轻量
//fun main() = runBlocking {
//    repeat(100_000){// 启动大量的协程
//        launch {
//            delay(5000L)
//            print(".")
//        }
//    }
//}

//它启动了 10 万个协程，并且在 5 秒钟后，每个协程都输出一个点。
//
//现在，尝试使用线程来实现。会发生什么？（很可能你的代码会产生某种内存不足的错误）



/*
全局协程像守护线程
以下代码在 GlobalScope 中启动了一个长期运行的协程，该协程每秒输出“I'm sleeping”两次，之后在主函数中延迟一段时间后返回。
 */

fun main() = runBlocking{
    GlobalScope.launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // 在延迟后退出
}
//I'm sleeping 0 ...
//I'm sleeping 1 ...
//I'm sleeping 2 ...

