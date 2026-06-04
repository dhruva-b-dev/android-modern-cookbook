package com.dhruva.kotlinadvancedapp

import android.R
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ScopeFunctionsTest {

    companion object {
        val numbers = mutableListOf("one", "two", "three", "four", "five")
    }

    @Test
    fun testLetExample() {
        val expected = listOf(5, 4, 4)
        val result = letExample(numbers)
        assertEquals("The expected list and result should be same!", expected, result)
    }

    @Test
    fun withExampleTest() {
        withExample(numbers)
    }

    @Test
    fun testRunExampleOdd() {
        val ipVal = 5
        val expected = false
        val result = runExample(ipVal)
        assertEquals("The expected and result value should be the same!", expected, result)
    }

    @Test
    fun testRunExampleEven() {
        val ipVal = 6
        val expected = true
        val result = runExample(ipVal)
        assertEquals("The expected and result value should be the same!", expected, result)
    }

    @Test
    fun testApplyExample(){
        applyExample()
    }

    @Test
    fun testAlsoExample(){
        alsoExample()
    }

    @Test
    fun testTakeExample(){
        takeExample()
    }
}