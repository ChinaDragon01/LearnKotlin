package com.test.learnkotlin

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/*
Delegated properties    https://kotlinlang.org/docs/delegated-properties.html

委托属性    https://www.kotlincn.net/docs/reference/delegated-properties.html

有一些常见的属性类型，虽然我们可以在每次需要的时候手动实现它们， 但是如果能够为大家把他们只实现一次并放入一个库会更好。例如包括：

延迟属性（lazy properties）: 其值只在首次访问时计算；
可观察属性（observable properties）: 监听器会收到有关此属性变更的通知；
把多个属性储存在一个映射（map）中，而不是每个存在单独的字段中。


标准委托
Kotlin 标准库为几种有用的委托提供了工厂方法。

延迟属性 Lazy
lazy() 是接受一个 lambda 并返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托： 第一次调用 get() 会执行已传递给 lazy() 的 lambda 表达式并记录结果， 后续调用 get() 只是返回记录的结果。


 */

fun main() {
    val e = Example()
    println(e.p)
    //com.test.learnkotlin.Example@617c74e5, thank you for delegating 'p' to me!

    e.p = "NEW"
    //NEW has been assigned to 'p' in com.test.learnkotlin.Example@617c74e5.

    println(lazyValue)
    println(lazyValue)
    //computed!
    //Hello
    //Hello

    //默认情况下，对于 lazy 属性的求值是同步锁的（synchronized）：该值只在一个线程中计算，并且所有线程会看到相同的值。
// 如果初始化委托的同步锁不是必需的，这样多个线程可以同时执行，那么将 LazyThreadSafetyMode.PUBLICATION 作为参数传递给 lazy() 函数。
// 而如果你确定初始化将总是发生在与属性使用位于相同的线程， 那么可以使用 LazyThreadSafetyMode.NONE 模式：它不会有任何线程安全的保证以及相关的开销。

    println(lazyValue2)
    println(lazyValue2)
    //computed 2 !
    //Hello 2
    //Hello 2

    val user = User()
    user.name = "first"
    user.name = "second"
    //<no name>  -> first
    //first  -> second
    println(user.name)
    //second


    val myClass = MyClass()
    // 通知：'oldName: Int' is deprecated.
    // Use 'newName' instead
    myClass.oldName = 42
    println(myClass.newName) // 42

    val user2 = User2(mapOf("name" to "kotlin", "age" to 20))
    println(user2.toString())
    //User2(name='kotlin', age=20)

    //委托属性会从这个映射中取值（通过字符串键——属性的名称）：
    println("user2.name = ${user2.name} , user2.age = ${user2.age}")
    //user2.name = kotlin , user2.age = 20
}

class Example {
    var p: String by Delegate()
}

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

val lazyValue2: String by lazy(LazyThreadSafetyMode.NONE) {
    println("computed 2 !")
    "Hello 2"
}

class User {
    //可观察属性 Observable
    //Delegates.observable() 接受两个参数：初始值与修改时处理程序（handler）。 每当我们给属性赋值时会调用该处理程序（在赋值后执行）。它有三个参数：被赋值的属性、旧值与新值：
    var name: String by Delegates.observable("<no name>") { prop, old, new ->
        println("$old  -> $new")
    }
}

/*

委托给另一个属性
从 Kotlin 1.4 开始，一个属性可以把它的 getter 与 setter 委托给另一个属性。这种委托对于顶层和类的属性（成员和扩展）都可用。该委托属性可以为：

顶层属性
同一个类的成员或扩展属性
另一个类的成员或扩展属性
为将一个属性委托给另一个属性，应在委托名称中使用恰当的 :: 限定符，例如，this::delegate 或 MyClass::delegate。


class MyClass(var memberInt: Int, val anotherClassInstance: ClassWithDelegate) {
    var delegatedToMember: Int by this::memberInt
    var delegatedToTopLevel: Int by ::topLevelInt

    val delegatedToAnotherClass: Int by anotherClassInstance::anotherClassInt
}
var MyClass.extDelegated: Int by ::topLevelInt


这是很有用的，例如，当想要以一种向后兼容的方式重命名一个属性的时候：引入一个新的属性、 使用 @Deprecated 注解来注解旧的属性、并委托其实现。

 */

class MyClass {
    var newName: Int = 0

    @Deprecated("Use 'newName' instead", ReplaceWith("newName"))
    var oldName: Int by this::newName
}

/*
将属性储存在映射中
一个常见的用例是在一个映射（map）里存储属性的值。 这经常出现在像解析 JSON 或者做其他“动态”事情的应用中。 在这种情况下，你可以使用映射实例自身作为委托来实现委托属性。
 */

class User2(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
    override fun toString(): String {
        return "User2(name='$name', age=$age)"
    }
}

//这也适用于 var 属性，如果把只读的 Map 换成 MutableMap 的话：
class MutableUser(var map: MutableMap<String, Any?>) {
    val name: String by map
    val age: Int by map
    override fun toString(): String {
        return "User2(name='$name', age=$age)"
    }
}


/*

局部委托属性
你可以将局部变量声明为委托属性。 例如，你可以使一个局部变量惰性初始化：

fun example(computeFoo: () -> Foo) {
    val memoizedFoo by lazy(computeFoo)
​
    if (someCondition && memoizedFoo.isValid()) {
        memoizedFoo.doSomething()
    }
}
memoizedFoo 变量只会在第一次访问时计算。 如果 someCondition 失败，那么该变量根本不会计算。

 */

/*

**************** 重点 *****************

属性委托要求
这里我们总结了委托对象的要求。

对于一个只读属性（即 val 声明的），委托必须提供一个操作符函数 getValue()，该函数具有以下参数：

thisRef —— 必须与 属性所有者 类型（对于扩展属性——指被扩展的类型）相同或者是其超类型。
property —— 必须是类型 KProperty<*> 或其超类型。
getValue() 必须返回与属性相同的类型（或其子类型）。

 */

class Resource
class Owner {
    val valResource: Resource by ResourceDelegate()
}

class ResourceDelegate {
    operator fun getValue(thisRef: Owner, property: KProperty<*>): Resource {
        return Resource()
    }
}

/*

对于一个可变属性（即 var 声明的），委托必须额外提供一个操作符函数 setValue()， 该函数具有以下参数：

thisRef —— 必须与 属性所有者 类型（对于扩展属性——指被扩展的类型）相同或者是其超类型。
property —— 必须是类型 KProperty<*> 或其超类型。
value — 必须与属性类型相同（或者是其超类型）。


 */

class Owner2 {
    var varResource: Resource by ResourceDelegate2()
}

class ResourceDelegate2(private var resource: Resource = Resource()) {
    operator fun getValue(thisRef: Owner2, property: KProperty<*>): Resource {
        return resource
    }

    operator fun setValue(thisRef: Owner2, property: KProperty<*>, value: Any?) {
        if (value is Resource) {
            resource = value
        }
    }
}

