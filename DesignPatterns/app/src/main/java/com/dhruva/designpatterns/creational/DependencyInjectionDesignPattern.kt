package com.dhruva.designpatterns.creational

/*
* Dependency Injection
* In DI, the object's dependencies are provided from an external
* source, instead of object creating it by itself.
* So it provides loose coupling, making the code easier to test and maintain.
*
* e.g. Dagger/Hilt
* */

//dependency
class Remote {
    fun powerOn(){
        println("Remote power on pressed...!")
    }
}

//Dependency injection, by using "constructor injection"
class Tv(private val remote: Remote){
    fun start(){
        remote.powerOn()
        println("Tv started...!")
    }
}