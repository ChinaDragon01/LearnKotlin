package com.test.learnkotlin

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis


/*
异步流
https://www.kotlincn.net/docs/reference/coroutines/flow.html

https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html
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
    /* launch {
         for (k in 1..3) {
             println("I'm not blocked $k")
             delay(100)
         }
     }

     simple3().collect { value ->
         println(value)
     }*/
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
    /* println("Calling simple function...")
     val flow = simple3()
     println("Calling collect...")
     flow.collect { value -> println(value) }
     println("Calling collect again...")
     flow.collect { value -> println(value) }*/
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
    /* withTimeoutOrNull(250) { // 在 250 毫秒后超时
         simple3().collect { value -> println(value) }
     }
     println("Done")*/
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
//    (1..3).asFlow().collect { value -> println(value) }


    println("--------------------------------------------------------------------------------------")


    /*
        过渡流操作符
        可以使用操作符转换流，就像使用集合与序列一样。 过渡操作符应用于上游流，并返回下游流。 这些操作符也是冷操作符，就像流一样。这类操作符本身不是挂起函数。它运行的速度很快，返回新的转换流的定义。

        基础的操作符拥有相似的名字，比如 map 与 filter。 流与序列的主要区别在于这些操作符中的代码可以调用挂起函数。

        举例来说，一个请求中的流可以使用 map 操作符映射出结果，即使执行一个长时间的请求操作也可以使用挂起函数来实现：

     */
    /*  (1..3).asFlow() // 一个请求流
          .map { request -> performRequest(request) }
          .collect { response -> println(response) }*/
    //response 1
    //response 2
    //response 3

    println("--------------------------------------------------------------------------------------")

    /*
        转换操作符
        在流转换操作符中，最通用的一种称为 transform。它可以用来模仿简单的转换，例如 map 与 filter，以及实施更复杂的转换。 使用 transform 操作符，我们可以 发射 任意值任意次。

        比如说，使用 transform 我们可以在执行长时间运行的异步请求之前发射一个字符串并跟踪这个响应：
     */
    /* (1..3).asFlow() // 一个请求流
         .transform { request ->
             emit("Making request $request")
             emit(performRequest(request))
         }
         .collect { response -> println(response) }*/
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
    /* numbers()
         .take(2) // 只获取前两个
         .collect { value -> println(value) }*/

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
    /* (1..5).asFlow()
         .filter {
             println("Filter $it")
             it % 2 == 0
         }
         .map {
             println("Map $it")
             "string $it"
         }.collect {
             println("Collect $it")
         }*/
    //Filter 1
    //Filter 2
    //Map 2
    //Collect string 2
    //Filter 3
    //Filter 4
    //Map 4
    //Collect string 4
    //Filter 5

    println("--------------------------------------------------------------------------------------")

    /*
        withContext 发出错误
        然而，长时间运行的消耗 CPU 的代码也许需要在 Dispatchers.Default 上下文中执行，并且更新 UI 的代码也许需要在 Dispatchers.Main 中执行。
        通常，withContext 用于在 Kotlin 协程中改变代码的上下文，但是 flow {...} 构建器中的代码必须遵循上下文保存属性，并且不允许从其他上下文中发射（emit）。

        尝试运行下面的代码：

     */

//    simple4().collect { value -> println(value) }
    /*
    Exception in thread "main" java.lang.IllegalStateException: Flow invariant is violated:
		Flow was collected in [BlockingCoroutine{Active}@1ae7591f, BlockingEventLoop@227d77ab],
		but emission happened in [DispatchedCoroutine{Active}@2b3743b3, Dispatchers.Default].
		Please refer to 'flow' documentation or use 'flowOn' instead
     */
    println("--------------------------------------------------------------------------------------")

    //flowOn 操作符        https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html
    //例外的是 flowOn 函数，该函数用于更改流发射的上下文。 以下示例展示了更改流上下文的正确方法，该示例还通过打印相应线程的名字以展示它们的工作方式：
//    simple5().collect { value ->
//        println("Collected $value")
//    }
    //Collected 1
    //Collected 2
    //Collected 3

    println("--------------------------------------------------------------------------------------")


    /*
        缓冲
        从收集流所花费的时间来看，将流的不同部分运行在不同的协程中将会很有帮助，特别是当涉及到长时间运行的异步操作时。
        例如，考虑一种情况， 一个 simple 流的发射很慢，它每花费 100 毫秒才产生一个元素；而收集器也非常慢， 需要花费 300 毫秒来处理元素。
        让我们看看从该流收集三个数字要花费多长时间：

     */

    /* val time = measureTimeMillis {
         simple3().collect { value ->
             delay(300) // 假装我们花费 300 毫秒来处理它
             println(value)
         }
     }
     println("Collected in $time ms")*/
    //它会产生这样的结果，整个收集过程大约需要 1200 毫秒（3 个数字，每个花费 400 毫秒）：
    //Flow started
    //1
    //2
    //3
    //Collected in 1242 ms

    //我们可以在流上使用 buffer 操作符来并发运行这个 simple 流中发射元素的代码以及收集的代码， 而不是顺序运行它们：
    /* val time = measureTimeMillis {
         simple3()
             .buffer() // 缓冲发射项，无需等待
             .collect { value ->
             delay(300) // 假装我们花费 300 毫秒来处理它
             println(value)
         }
     }
     println("Collected in $time ms")*/
    //它产生了相同的数字，只是更快了，由于我们高效地创建了处理流水线，
// 仅仅需要等待第一个数字产生的 100 毫秒以及处理每个数字各需花费的 300 毫秒。这种方式大约花费了 1000 毫秒来运行：
    //Flow started
    //1
    //2
    //3
    //Collected in 1086 ms

    /*
        合并
        当流代表部分操作结果或操作状态更新时，可能没有必要处理每个值，而是只处理最新的那个。
        在本示例中，当收集器处理它们太慢的时候， conflate 操作符可以用于跳过中间值。构建前面的示例：
     */

    /*  val time = measureTimeMillis {
          simple3()
              .conflate() // 合并发射项，不对每个值进行处理
              .collect { value ->
                  delay(300) // 假装我们花费 300 毫秒来处理它
                  println(value)
              }
      }
      println("Collected in $time ms")*/

    //我们看到，虽然第一个数字仍在处理中，但第二个和第三个数字已经产生，因此第二个是 conflated ，只有最新的（第三个）被交付给收集器：
    //Flow started
    //1
    //3
    //Collected in 779 ms


    /*
        处理最新值
        当发射器和收集器都很慢的时候，合并是加快处理速度的一种方式。它通过删除发射值来实现。 另一种方式是取消缓慢的收集器，并在每次发射新值的时候重新启动它。
        有一组与 xxx 操作符执行相同基本逻辑的 xxxLatest 操作符，但是在新值产生的时候取消执行其块中的代码。让我们在先前的示例中尝试更换 conflate 为 collectLatest：
     */
    /* val time = measureTimeMillis {
         simple3()
             .collectLatest { value -> // 取消并重新发射最后一个值
                 println("Collecting $value")
                 delay(300) // 假装我们花费 300 毫秒来处理它
                 println("Done $value")
             }
     }
     println("Collected in $time ms")*/
    //由于 collectLatest 的函数体需要花费 300 毫秒，但是新值每 100 秒发射一次，我们看到该代码块对每个值运行，但是只收集最后一个值：
    //Flow started
    //Collecting 1
    //Collecting 2
    //Collecting 3
    //Done 3
    //Collected in 688 ms

    /*
        组合多个流
        Zip
        就像 Kotlin 标准库中的 Sequence.zip 扩展函数一样， 流拥有一个 zip 操作符用于组合两个流中的相关值：
     */

    /* val nums = (1..3).asFlow()
     var stars = flowOf("one", "two", "three")
     nums.zip(stars) { a, b ->
         "$a -> $b"// 组合单个字符串
     }.collect {
         println(it)
     }*/

    //1 -> one
    //2 -> two
    //3 -> three

    /*
    combine
    v. （使）结合，组合；（使）化合；合并，协力；兼有，兼备；兼做，兼办
    n. 联盟，联合体；联合收割机


        Combine
        当流表示一个变量或操作的最新值时（请参阅相关小节 conflation），可能需要执行计算，这依赖于相应流的最新值，
        并且每当上游流产生值的时候都需要重新计算。这种相应的操作符家族称为 combine。
     */
//    val nums = (1..3).asFlow().onEach { delay(300) } // 发射数字 1..3，间隔 300 毫秒
//    val strs = flowOf("one", "two", "three").onEach { delay(400) } // 每 400 毫秒发射一次字符串
//    val startTime = System.currentTimeMillis()// 记录开始的时间

   /* nums.zip(strs) { a, b -> "$a -> $b" }//使用“zip”组合单个字符串
        .collect { value -> // 收集并打印
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }*/
    //1 -> one at 437 ms from start
    //2 -> two at 842 ms from start
    //3 -> three at 1245 ms from start

    //当在这里使用 combine 操作符来替换 zip：
   /* nums.combine(strs) { a, b -> "$a -> $b" }//使用“zip”组合单个字符串
        .collect { value -> // 收集并打印
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }*/
    //得到了完全不同的输出，其中，nums 或 strs 流中的每次发射都会打印一行：
    //1 -> one at 451 ms from start
    //2 -> one at 668 ms from start
    //2 -> two at 858 ms from start
    //3 -> two at 983 ms from start
    //3 -> three at 1265 ms from start

    /*
        展平流
        流表示异步接收的值序列，所以很容易遇到这样的情况： 每个值都会触发对另一个值序列的请求。比如说，我们可以拥有下面这样一个返回间隔 500 毫秒的两个字符串流的函数：

        现在，如果我们有一个包含三个整数的流，并为每个整数调用 requestFlow，如下所示：
     */
//    (1..3).asFlow().map { requestFlow(it) }
    /*
        然后我们得到了一个包含流的流（Flow<Flow<String>>），需要将其进行展平为单个流以进行下一步处理。
        集合与序列都拥有 flatten 与 flatMap 操作符来做这件事。然而，由于流具有异步的性质，因此需要不同的展平模式， 为此，存在一系列的流展平操作符。
     */


    /*
        flatMapConcat   https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flat-map-concat.html
        连接模式由 flatMapConcat 与 flattenConcat 操作符实现。它们是相应序列操作符最相近的类似物。它们在等待内部流完成之前开始收集下一个值，如下面的示例所示：
     */
   /* val startTime = System.currentTimeMillis() // 记录开始时间
    (1..3).asFlow().onEach { delay(100) }// 每 100 毫秒发射一个数字
        .flatMapConcat { requestFlow(it) }
        .collect { value -> // 收集并打印
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }*/
    //1: First at 120 ms from start
    //1: Second at 624 ms from start
    //2: First at 736 ms from start
    //2: Second at 1246 ms from start
    //3: First at 1354 ms from start
    //3: Second at 1856 ms from start

    /*
        flatMapMerge
        另一种展平模式是并发收集所有传入的流，并将它们的值合并到一个单独的流，以便尽快的发射值。 它由 flatMapMerge 与 flattenMerge 操作符实现。
        他们都接收可选的用于限制并发收集的流的个数的 concurrency 参数（默认情况下，它等于 DEFAULT_CONCURRENCY）。
     */

    val startTime = System.currentTimeMillis() // 记录开始时间
    (1..3).asFlow().onEach { delay(100) } // 每 100 毫秒发射一个数字
        .flatMapMerge { requestFlow(it) }
        .collect { value -> // 收集并打印
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
    //1: First at 148 ms from start
    //2: First at 247 ms from start
    //3: First at 361 ms from start
    //1: Second at 651 ms from start
    //2: Second at 757 ms from start
    //3: Second at 873 ms from start


}
fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500) // 等待 500 毫秒
    emit("$i: Second")
}


//flowOn
fun simple5(): Flow<Int> = flow {
    // 在流构建器中更改消耗 CPU 代码的上下文的错误方式

    for (i in 1..3) {
        Thread.sleep(100) // 假装我们以消耗 CPU 的方式进行计算
        emit(i) // 发射下一个值
    }

}.flowOn(Dispatchers.Default)// 在流构建器中改变消耗 CPU 代码上下文的正确方式

fun simple4(): Flow<Int> = flow {
    // 在流构建器中更改消耗 CPU 代码的上下文的错误方式
    withContext(Dispatchers.Default) {
        for (i in 1..3) {
            Thread.sleep(100) // 假装我们以消耗 CPU 的方式进行计算
            emit(i) // 发射下一个值
        }
    }
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