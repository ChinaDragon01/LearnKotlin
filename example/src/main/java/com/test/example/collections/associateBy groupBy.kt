package com.test.example.collections

/*
associateBy, groupBy    https://play.kotlinlang.org/byExample/05_Collections/10_associateBy
 */

fun main() {
    val people = listOf(
        Person("John", "Boston", "+1-888-123456"),
        Person("Sarah", "Munich", "+49-777-789123"),
        Person("Svyatoslav", "Saint-Petersburg", "+7-999-456789"),
        Person("Vasilisa", "Saint-Petersburg", "+7-999-123456"))

    val phoneBook = people.associateBy { it.phone }
    val cityBook = people.associateBy(Person::phone, Person::city)
    val peopleCities = people.groupBy(Person::city, Person::name)
    val lastPersonCity = people.associateBy(Person::city, Person::name)

    println("phoneBook = $phoneBook")
    println("cityBook = $cityBook")
    println("peopleCities = $peopleCities")
    println("lastPersonCity = $lastPersonCity")
    //phoneBook = {+1-888-123456=Person(name=John, city=Boston, phone=+1-888-123456), +49-777-789123=Person(name=Sarah, city=Munich, phone=+49-777-789123), +7-999-456789=Person(name=Svyatoslav, city=Saint-Petersburg, phone=+7-999-456789), +7-999-123456=Person(name=Vasilisa, city=Saint-Petersburg, phone=+7-999-123456)}
    //cityBook = {+1-888-123456=Boston, +49-777-789123=Munich, +7-999-456789=Saint-Petersburg, +7-999-123456=Saint-Petersburg}
    //peopleCities = {Boston=[John], Munich=[Sarah], Saint-Petersburg=[Svyatoslav, Vasilisa]}
    //lastPersonCity = {Boston=John, Munich=Sarah, Saint-Petersburg=Vasilisa}

}

data class Person(val name: String, val city: String, val phone: String)