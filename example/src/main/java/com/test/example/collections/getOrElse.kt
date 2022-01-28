package com.test.example.collections

/*
getOrElse       https://play.kotlinlang.org/byExample/05_Collections/17_getOrElse

getOrElse provides safe access to elements of a collection. It takes an index and a function that provides the default value in cases when the index is out of bound.

getOrElse提供了对集合元素的安全访问。它接受一个索引和一个在索引超出范围时提供默认值的函数。

 */

fun main(){
    val list = listOf(0, 10, 20)
    println(list.getOrElse(1) { 42 })
    println(list.getOrElse(10) { 42 })
    //10
    //42

}