package com.test.example.specialClasses

/*
Sealed Classes
https://play.kotlinlang.org/byExample/03_special_classes/03_Sealed%20Classes
密封类允许您限制继承的使用。 一旦你声明了一个密封类，它就只能从声明密封类的同一个包中子类化。 它不能在声明密封类的包之外进行子类化。
 */

sealed class Mammal(val name: String)
class Cat(val catName: String) : Mammal(catName)
class Human(val humanName: String, val job: String) : Mammal(humanName)

fun greetMammal(mammal: Mammal): String {
    when (mammal) {
        is Human -> return "Hello ${mammal.name}; You're working as a ${mammal.job}"
        is Cat -> return "Hello ${mammal.name}"
    }
}

fun main() {
    println(greetMammal(Cat("Snowy")))
    //Hello Snowy
}