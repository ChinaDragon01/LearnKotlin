package com.test.example.specialClasses

/*
Data Classes    https://play.kotlinlang.org/byExample/03_special_classes/01_Data%20classes
数据类使得创建用于存储值的类变得很容易。这些类自动提供了复制、获取字符串表示和使用集合中的实例的方法。您可以在类声明中使用自己的实现重写这些方法。
 */
data class User(val name: String, val id: Int) {           // 1
    override fun equals(other: Any?) =
        other is User && other.id == this.id               // 2
}
fun main() {
    val user = User("Alex", 1)
    println(user)                                          // 3

    val secondUser = User("Alex", 1)
    val thirdUser = User("Max", 2)

    println("user == secondUser: ${user == secondUser}")   // 4
    println("user == thirdUser: ${user == thirdUser}")

    // hashCode() function
    println(user.hashCode())                               // 5
    println(secondUser.hashCode())
    println(thirdUser.hashCode())

    // copy() function
    println(user.copy())                                   // 6
    println(user === user.copy())                          // 7
    println(user.copy("Max"))                              // 8
    println(user.copy(id = 3))                             // 9
    println("name = ${user.component1()}")                 // 10
    println("id = ${user.component2()}")
    //User(name=Alex, id=1)
    //user == secondUser: true
    //user == thirdUser: false
    //63347075
    //63347075
    //2390846
    //User(name=Alex, id=1)
    //false
    //User(name=Max, id=1)
    //User(name=Alex, id=3)
    //name = Alex
    //id = 1
}