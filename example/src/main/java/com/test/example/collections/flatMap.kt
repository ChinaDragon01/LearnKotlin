package com.test.example.collections

/*
flatMap https://play.kotlinlang.org/byExample/05_Collections/12_flatMap

flatMap transforms each element of a collection into an iterable object and builds a single list of the transformation results. The transformation is user-defined.

flatMap将集合中的每个元素转换为一个可迭代对象，并构建一个转换结果列表。转换是用户定义的。

 */
fun main(){

    val fruitsBag = listOf("apple","orange","banana","grapes")
    val clothesBag = listOf("shirts","pants","jeans")
    val cart = listOf(fruitsBag, clothesBag)
    val mapBag = cart.map { it }
    val flatMapBag = cart.flatMap { it }

    println("mapBag = $mapBag")
    println("flatMapBag = $flatMapBag")

    //mapBag = [[apple, orange, banana, grapes], [shirts, pants, jeans]]
    //flatMapBag = [apple, orange, banana, grapes, shirts, pants, jeans]

}