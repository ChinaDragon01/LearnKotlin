package com.test.example.collections

/*
Set     https://play.kotlinlang.org/byExample/05_Collections/02_Set
 */

fun main(){
    val aNewIssue: String = "uniqueDescr4"
    val anIssueAlreadyIn: String = "uniqueDescr2"

    println("Issue $aNewIssue ${getStatusLog(addIssue(aNewIssue))}")                              // 4
    println("Issue $anIssueAlreadyIn ${getStatusLog(addIssue(anIssueAlreadyIn))}")
    //Issue uniqueDescr4 registered correctly.
    //Issue uniqueDescr2 marked as duplicate and rejected.
}

val openIssues: MutableSet<String> = mutableSetOf("uniqueDescr1", "uniqueDescr2", "uniqueDescr3")
fun addIssue(uniqueDesc: String): Boolean {
    return openIssues.add(uniqueDesc)                                                             // 2
}

fun getStatusLog(isAdded: Boolean): String {
    return if (isAdded) "registered correctly." else "marked as duplicate and rejected."          // 3
}
