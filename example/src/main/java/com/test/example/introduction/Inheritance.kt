package com.test.learnkotlin

/*
    Inheritance 继承
    https://play.kotlinlang.org/byExample/01_introduction/07_Inheritance
 */
fun main() {

    val tiger: Tiger = SiberianTiger()
    tiger.sayHello()

    val lion: Lion = Asiatic("Rufo")
    lion.sayHello()

}


open class Tiger(var origin: String) {
    //origin 起源
    fun sayHello() {
        println("A tiger from $origin says: grrhhh!")
    }
}

class SiberianTiger : Tiger("Siberia")//Siberia 西伯利亚


open class Lion(val name: String, val origin: String) {
    fun sayHello() {
        println("$name, the lion from $origin says: graoh!")
    }
}

class Asiatic(name: String) : Lion(name = name, origin = "India") // 1