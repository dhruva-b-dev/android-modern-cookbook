package com.dhruva.designpatterns.structural

/*
* Decorator Design Pattern
* Decorator pattern is used to modify or alter the functionality of the object at runtime
* by wrapping them in an object of a Decorator class.
* This provides a flexible alternative to using inheritance to modify behavior.
* e.g. Wrapping InputStream in BufferedInputStream is an example.
* */

// base interface
interface DataService {
    fun fetchData(): String
}

//concrete class
class SimpleDataService : DataService {
    override fun fetchData() = "Original data!"
}

//adding additional decorator for logging feature
class LoggingDecorator(private val inner: DataService) : DataService {
    override fun fetchData(): String {
        println("Log : Starting fetching data...")
        val result = inner.fetchData()
        println("Log : Data fetching completed")
        return result
    }
}

//cleaner decorator using class delegation
class CachingDecorator(private val inner: DataService) : DataService by inner {
    var cachedData: String? = null
    override fun fetchData(): String {
        return cachedData ?: inner.fetchData().also { cachedData = it }
    }
}