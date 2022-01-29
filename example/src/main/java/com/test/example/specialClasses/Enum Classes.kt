package com.test.example.specialClasses

/*
Enum Classes
https://play.kotlinlang.org/byExample/03_special_classes/02_Enum
枚举类用于为表示有限组不同值的类型建模，例如方向、状态、模式等等。

 */

enum class State{
    IDLE, RUNNING, FINISHED
}

//枚举可以像其他类一样包含属性和方法，用分号与枚举常量列表分开。
enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF),
    YELLOW(0xFFFF00);

    fun containsRed() = (this.rgb and 0xFF0000 != 0)
}


fun main() {
    val state = State.RUNNING
    val message = when (state) {
        State.IDLE -> "It's idle"
        State.RUNNING -> "It's running"
        State.FINISHED -> "It's finished"
    }
    println(message)

    val red = Color.RED
    println(red)
    println(red.containsRed())
    println(Color.BLUE.containsRed())
    println(Color.YELLOW.containsRed())
    //It's running
    //RED
    //true
    //false
    //true
}