package com.test.example.collections

/*
any, all, none      https://play.kotlinlang.org/byExample/05_Collections/05_existential
 */

fun main() {
    //Function any
    //Function any returns true if the collection contains at least one element that matches the given predicate.
    val numbers = listOf(1, -2, 3, -4, 5, -6, 7)

    val anyNegative = numbers.any { it < 0 }             //  只要有一个小于 0 的 返回 true

    val anyGT6 = numbers.any { it > 6 }                  // 只要有一个大于 6 的 返回 true

    println("anyNegative  = $anyNegative ,  anyGT6 = $anyGT6")
    //anyNegative  = true ,  anyGT6 = true


    //Function all
    //Function all returns true if all elements in collection match the given predicate.
    val allEven = numbers.all { it % 2 == 0 }
    val allLess6 = numbers.all { it < 6 }
    println("allEven  = $allEven ,  allLess6 = $allLess6")
    //allEven  = false ,  allLess6 = false


    //Function none
    //Function none returns true if there are no elements that match the given predicate in the collection.
    val noneEven = numbers.none { it % 2 == 1 }
    val noneLess6 = numbers.none { it > 6 }
    println("noneEven  = $noneEven ,  noneLess6 = $noneLess6")
    //noneEven  = false ,  noneLess6 = false


    //count
    //count functions returns either the total number of elements in a collection or the number of elements matching the given predicate.
    val totalCount = numbers.count()
    val evenCount = numbers.count { it % 2 == 0 }
    println("totalCount  = $totalCount ,  evenCount = $evenCount")
    //totalCount  = 7 ,  evenCount = 3


    //partition
    //The partition function splits the original collection into a pair of lists using a given predicate:
    //Elements for which the predicate is true.
    //Elements for which the predicate is false.
    val evenOdd = numbers.partition { it % 2 == 0 }
    val (positives, negatives) = numbers.partition { it > 0 }
    println("evenOdd  = $evenOdd ,  positives = $positives  ， negatives = $negatives")
    //evenOdd  = ([-2, -4, -6], [1, 3, 5, 7]) ,  positives = [1, 3, 5, 7]  ， negatives = [-2, -4, -6]


}