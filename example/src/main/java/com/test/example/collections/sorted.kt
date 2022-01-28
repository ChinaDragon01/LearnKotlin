package com.test.example.collections

import kotlin.math.abs

/*
sorted  https://play.kotlinlang.org/byExample/05_Collections/14_sorted

sorted returns a list of collection elements sorted according to their natural sort order (ascending).
sortedBy sorts elements according to natural sort order of the values returned by specified selector function.

Sorted返回按照自然排序顺序(升序)排序的集合元素列表。

sortedBy按照指定选择器函数返回值的自然排序顺序对元素进行排序。
 */

fun main(){
    val shuffled = listOf(5, 4, 2, 1, 3, -10)                   // 1
    val natural = shuffled.sorted()                             // 2
    val inverted = shuffled.sortedBy { -it }                    // 3
    val descending = shuffled.sortedDescending()                // 4
    val descendingBy = shuffled.sortedByDescending { abs(it)  }

    println("natural = $natural")
    println("inverted = $inverted")
    println("descending = $descending")
    println("descendingBy = $descendingBy")

    //natural = [-10, 1, 2, 3, 4, 5]
    //inverted = [5, 4, 3, 2, 1, -10]
    //descending = [5, 4, 3, 2, 1, -10]
    //descendingBy = [-10, 5, 4, 3, 2, 1
}