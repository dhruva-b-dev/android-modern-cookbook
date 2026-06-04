package com.dhruva.kotlinadvancedapp

import org.junit.Test

class LateInitAndLazyTest {

    @Test
    fun testLateInit() {
        val userManager = UserManager()
        // userManager.printDetails() // This would print nothing as it's not initialized
        
        userManager.initializeUser()
        userManager.printDetails()
    }

    @Test
    fun testLazy() {
        val db = DatabaseConnection()
        println("Object created, but expensiveData is not initialized yet.")

        // 'lazy' initialization happens exactly here
        println(db.expensiveData)
        
        // Second access uses the cached value
        println("Second access: ${db.expensiveData}")
    }
}
