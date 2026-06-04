package com.dhruva.kotlinadvancedapp

import kotlin.random.Random


/*
* Scope functions - functions whose sole purpose is to execute a code
* of block within the context of an object.
* */

/*
* let
* context object - available as an argument (it)
* return value - lambda result
* can be used to invoke one or more functions on results of call chains.
* - Executing a lambda on non-nullable objects
* - Introducing an expression as a variable in local scope
* */
fun letExample(numbers: List<String>): List<Int> {
    numbers.map { it.length }.filter { it > 3 }.let {
        return it
    }
}

/*
* with
*The context object - available as a receiver (this).
*The return value - lambda result.
* with this object, do the following.
* Grouping function calls on an object
* */
fun withExample(numbers: List<String>) {
    with(numbers) {
        println("'with' is called with argument $this")
        println("It contains $size elements")
    }
}

/*
* run
* The context object - receiver(this)
* return value - lambda result
* - object configuration and computing the result
* - does the same as with, but it is implemented as an extension function.
* */
fun runExample(number: Int): Boolean {
    return number.run {
        this % 2 == 0
    }
}

data class Person(var name: String = "", var age: Int = 0, var city: String = "")

/*
* apply
* the context object - receiver(this)
* return value - the object itself
* most common use case for apply is object configuration
* */
fun applyExample() {
    val adam = Person(name = "Adam").apply {
        city = "Baroda"
        age = 30
    }
    println(adam)
}

/*
* also
* context object - argument (it)
* return value - object itself
* also in code, you can read it as " and also do the following with the object. "
* */
fun alsoExample() {
    val numbers = mutableListOf<String>("one", "two", "three")
    numbers.also { println("The list element before adding the new one : $it") }
        .add("four")
    println("after adding new : $numbers")
}

fun takeExample() {
    val number = Random.nextInt(100)
    val evenOrNull = number.takeIf { it % 2 == 0 }
    val oddOrNull = number.takeUnless { it % 2 == 0 }
    println("EvenOrNull : $evenOrNull oddOrNull : $oddOrNull")
}