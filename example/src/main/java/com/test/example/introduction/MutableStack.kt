package com.test.learnkotlin

/*
        Generics 泛型
        https://play.kotlinlang.org/byExample/01_introduction/06_Generics
 */
fun main() {
    val stack = mutableStackOf(0.62, 3.14, 2.7)
    println(stack)

}


class MutableStack<E>(vararg items: E) {
    private val elements = items.toMutableList()
    fun push(element: E) = elements.add(element)
    fun peek(): E = elements.last()
    fun pop(): E = elements.removeAt(elements.size - 1)
    fun isEmpty(): Boolean = elements.isEmpty()
    fun size() = elements.size
    override fun toString(): String {
        return "MutableStack（${elements.joinToString()}）"
    }

}

/*
    泛型函数
 */
fun <E> mutableStackOf(vararg element: E) = MutableStack(*element)

