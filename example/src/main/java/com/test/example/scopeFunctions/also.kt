package com.test.example.scopeFunctions

/*
also    https://play.kotlinlang.org/byExample/06_scope_functions/05_also
它也像apply一样工作:它执行一个给定的块并返回被调用的对象。在块内部，对象被它引用，所以它更容易作为参数传递。这个函数可以方便地嵌入其他操作，比如在调用链中登录。
 */
fun main() {
    val jake = Person2().also {

    }

    getPerson().also {
        it.token?.let { tempToen -> login(tempToen) }
    }
}

class Person2(
    var name: String = "test",
    var age: Int = 0,
    var about: String? = null
) {
    var token: String? = null
    override fun toString(): String {
        return "Person(name=$name, age=$age, about=$about)"
    }
}

fun login(toke: String) {

}

fun getPerson(): Person2 = Person2()