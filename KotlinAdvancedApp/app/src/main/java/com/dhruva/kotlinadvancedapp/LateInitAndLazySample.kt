package com.dhruva.kotlinadvancedapp

//for lateInit
class UserManager() {
    //declare lateinit property without initializing it
    lateinit var userName: String

    fun initializeUser() {
        //initialize it, consider it might be coming from
        //and api call/database
        userName = "Alice"
    }

    fun printDetails() {
        //check before using it, if it's initialized or not.
        //"::" - this is needed to check KProperty itself,
        //as accessing the property directly, if it's not initialized,
        //would cause and exception.
        if (::userName.isInitialized) {
            println("User: $userName")
        }
    }
}

//for lazy
// 1. Initialize using 'by lazy'
// The lambda block only runs the first time 'expensiveData' is called
class DatabaseConnection{
    val expensiveData : String by lazy {
        println("Connecting to database and fetching data... (This only happens once!)")
        "Data from Database"
    }
}

//Note : For checking output, refer the relevant unit tests.
