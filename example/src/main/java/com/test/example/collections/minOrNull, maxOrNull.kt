package com.test.example.collections

/*
minOrNull, maxOrNull    https://play.kotlinlang.org/byExample/05_Collections/13_max

minOrNull and maxOrNull functions return the smallest and the largest element of a collection. If the collection is empty, they return null.

minOrNull和 maxOrNull函数返回集合中最小和最大的元素。如果集合为空，则返回null。

 */
fun main(){
    val numbers = listOf(1, 2, 3)
    val empty = emptyList<Int>()
    val only = listOf(3)

    println("Numbers: $numbers, min = ${numbers.minOrNull()} max = ${numbers.maxOrNull()}")
    println("Empty: $empty, min = ${empty.minOrNull()}, max = ${empty.maxOrNull()}")
    println("Only: $only, min = ${only.minOrNull()}, max = ${only.maxOrNull()}")
    //Numbers: [1, 2, 3], min = 1 max = 3
    //Empty: [], min = null, max = null
    //Only: [3], min = 3, max = 3
}