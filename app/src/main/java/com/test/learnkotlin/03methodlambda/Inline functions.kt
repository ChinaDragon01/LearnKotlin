package com.test.learnkotlin

import java.util.concurrent.locks.Lock

/*

Inline functions        https://kotlinlang.org/docs/inline-functions.html

内联函数    https://www.kotlincn.net/docs/reference/inline-functions.html

使用高阶函数会带来一些运行时的效率损失：每一个函数都是一个对象，并且会捕获一个闭包。 即那些在函数体内会访问到的变量。 内存分配（对于函数对象和类）和虚拟调用会引入运行时间开销。

但是在许多情况下通过内联化 lambda 表达式可以消除这类的开销。 下述函数是这种情况的很好的例子。即 lock() 函数可以很容易地在调用处内联。 考虑下面的情况：

lock(l) { foo() }


编译器没有为参数创建一个函数对象并生成一个调用。取而代之，编译器可以生成以下代码：

l.lock()
try {
    foo()
}
finally {
    l.unlock()
}
这个不是我们从一开始就想要的吗？

为了让编译器这么做，我们需要使用 inline 修饰符标记 lock() 函数：

inline fun <T> lock(lock: Lock, body: () -> T): T { …… }
inline 修饰符影响函数本身和传给它的 lambda 表达式：所有这些都将内联到调用处。

内联可能导致生成的代码增加；不过如果我们使用得当（即避免内联过大函数），性能上会有所提升，尤其是在循环中的“超多态（megamorphic）”调用处。


 */
fun main(){

}
