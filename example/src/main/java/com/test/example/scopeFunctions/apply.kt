package com.test.example.scopeFunctions

/*
apply
https://play.kotlinlang.org/byExample/06_scope_functions/04_apply
Apply在对象上执行代码块并返回对象本身。在块内部，对象被this引用。这个函数对于初始化对象很方便。
 */
fun main() {

    val jake = Person()

    val stringDescription = jake.apply {
        name = "Jake"
        age = 30
        about = "Android developer"
    }.toString()

    println("stringDescription  = $stringDescription")
    //stringDescription  = Person(name=Jake, age=30, about=Android developer)

}

class Person() {
    var name: String? = null
    var age: Int = 20
    var about: String? = null
    override fun toString(): String {
        return "Person(name=$name, age=$age, about=$about)"
    }
}