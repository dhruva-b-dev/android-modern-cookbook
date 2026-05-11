package com.dhruva.designpatterns.creational

/*This is an example of Singleton Design Pattern
Singleton is an Object, which has a single instance
throughout entire codebase.
In Kotlin - using Object keyword.
In Java - using final keyword.

E.g. SharedPreference, Retrofit, Room database instance
*/
object SingletonDesignPattern {
    val instanceName: String = "SingletonInstance"
    fun testSingletonDesignPattern() {
        println("Hi! This is Singleton Design Pattern with instance : $instanceName")
    }
}