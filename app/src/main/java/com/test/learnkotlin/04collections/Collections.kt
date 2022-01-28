package com.test.learnkotlin

import java.security.Key

/*

Kotlin 集合概述 https://www.kotlincn.net/docs/reference/collections-overview.html#%E9%9B%86%E5%90%88%E7%B1%BB%E5%9E%8B
Kotlin 标准库提供了基本集合类型的实现： set、list 以及 map。 一对接口代表每种集合类型：

一个 只读 接口，提供访问集合元素的操作。
一个 可变 接口，通过写操作扩展相应的只读接口：添加、删除和更新其元素。
请注意，更改可变集合不需要它是以 var 定义的变量：写操作修改同一个可变集合对象，因此引用不会改变。 但是，如果尝试对 val 集合重新赋值，你将收到编译错误。

val numbers = mutableListOf("one", "two", "three", "four")
numbers.add("five")   // 这是可以的
//numbers = mutableListOf("six", "seven")      // 编译错误


List 相关操作       https://www.kotlincn.net/docs/reference/list-operations.html


Set 相关操作    https://www.kotlincn.net/docs/reference/set-operations.html

Set
Set<T> 存储唯一的元素；它们的顺序通常是未定义的。null 元素也是唯一的：一个 Set 只能包含一个 null。
当两个 set 具有相同的大小并且对于一个 set 中的每个元素都能在另一个 set 中存在相同元素，则两个 set 相等

Set的默认实现 - LinkedHashSet – 保留元素插入的顺序。



Map 相关操作 https://www.kotlincn.net/docs/reference/map-operations.html

 */

fun main() {

    val numbers1 = listOf(1, 2, 3, 4, 5)
    println(numbers1[0])
    println(numbers1.get(1))

    //1
    //2

    val numbers2 = (0..13).toList()
    //取列表的一部分
    println(numbers2)
    //[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]
    println(numbers2.subList(3, 6))
    //[3, 4, 5]

    //在有序列表中二分查找
    val numbers3 = (1..13).toList()
    println(numbers3)
    //[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]
    println("6 对应的下标 = " + numbers3.binarySearch(6))
    //6 对应的下标 = 5

    val setnumbers = setOf(1, 2, 3, 4)
    println("Number of elements: ${setnumbers.size}")
    if (setnumbers.contains(1)) println("1 is in the set")

    val numbersBackwards = setOf(4, 3, 2, 1)
    println("The sets are equal: ${setnumbers == numbersBackwards}")

    //Number of elements: 4
    //1 is in the set
    //The sets are equal: true

    //Set的默认实现 - LinkedHashSet – 保留元素插入的顺序。
    println("numbersBackwards.first() = ${numbersBackwards.first()}  ,  numbersBackwards.last() = ${numbersBackwards.last()} ")
    //numbersBackwards.first() = 4  ,  numbersBackwards.last() = 1


    //List 写操作
    //listOf 不可变 ， mutableListOf 可变

    val mutableList = mutableListOf(1, 2, 3)
    mutableList.add(4)
    mutableList.add(5)
    println(mutableList)
    //[1, 2, 3, 4, 5]


    //mapof 不可变  mutableMapOf可变

    val mapof = mapOf(" one" to 1, "kotlin" to " android")
    val mutableMap = mutableMapOf(" one" to 1, "kotlin" to " android")
    mutableMap.put("java ", "android")
    mutableMap.put("five", 5)
    mutableMap.put("five2", 5)
    println(mutableMap)
    //{ one=1, kotlin= android, java =android, five=5, five2=5}
    //过滤
    //可以使用 filter() 函数来过滤 map 或其他集合
    val filteredMap = mutableMap.filter { (key,value) -> key.endsWith("five") && value == 5 }
    println(filteredMap)
    //{five=5}

}