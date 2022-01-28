package com.test.example.collections

/*
Map Element Access  https://play.kotlinlang.org/byExample/05_Collections/15_Map_getValue

When applied to a map, [] operator returns the value corresponding to the given key, or null if there is no such key in the map.
getValue function returns an existing value corresponding to the given key or throws an exception if the key wasn't found.
For maps created with withDefault, getValue returns the default value instead of throwing an exception.


 */

fun main(){
    val map = mapOf("key" to 42)

    val value1 = map["key"]
    val value2 = map["key2"]

    val value3: Int = map.getValue("key")

    val mapWithDefault = map.withDefault { k -> k.length }
    val value4 = mapWithDefault.getValue("key2")

    val tempV = map.get("key")

    try {
        val temp = map.getValue("anotherKey")
        println("temp = $temp")
    } catch (e: NoSuchElementException) {
        println("Message: $e")
    }

    println("value1 = $value1 , value2 = $value2 , value3 = $value3 , mapWithDefault = $mapWithDefault , value4 = $value4 , tempV = $tempV")
    //Message: java.util.NoSuchElementException: Key anotherKey is missing in the map.
    //value1 = 42 , value2 = null , value3 = 42 , mapWithDefault = {key=42} , value4 = 4 , tempV = 42
}