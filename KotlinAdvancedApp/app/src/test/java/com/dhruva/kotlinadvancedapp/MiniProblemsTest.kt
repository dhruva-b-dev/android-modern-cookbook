package com.dhruva.kotlinadvancedapp

import junit.framework.TestCase.assertEquals
import org.junit.Test

class MiniProblemsTest {

    @Test
    fun testRemoveDuplicates() {
        val arrayList = arrayListOf(
            "abc",
            "a",
            "b",
            "c",
            "abc"
        )
        val expected = arrayListOf(
            "abc",
            "a",
            "b",
            "c"
        )
        val result = removeDuplicates(arrayList)
        assertEquals("The result list should be same as expected", expected, result)
    }
}