package com.test.example.collections

/*
zip     https://play.kotlinlang.org/byExample/05_Collections/16_zip

zip function merges two given collections into a new collection. By default, the result collection contains Pairs of source collection elements with the same index.
However, you can define your own structure of the result collection element.
The size of the result collection equals to the minimum size of a source collection.

函数将两个给定的集合合并成一个新的集合。默认情况下，结果集合包含具有相同索引的源集合元素对。

但是，您可以定义自己的结果集合元素的结构。

结果集合的大小等于源集合的最小大小。

 */

fun main(){
    val A = listOf("a", "b", "c")
    val B = listOf(1, 2, 3, 4)

    val resultPairs = A zip B
    val resultReduce = A.zip(B) { a, b -> "$a$b" }

    println("resultPairs = $resultPairs")
    println("resultReduce = $resultReduce ")
    //resultPairs = [(a, 1), (b, 2), (c, 3)]
    //resultReduce = [a1, b2, c3]



}