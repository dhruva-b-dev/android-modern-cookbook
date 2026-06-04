package com.dhruva.kotlinadvancedapp

import com.dhruva.kotlinadvancedapp.SampleSnippets.addDollarSuffix
import com.dhruva.kotlinadvancedapp.SampleSnippets.process
import com.dhruva.kotlinadvancedapp.SampleSnippets.returnMeAddFun
import com.dhruva.kotlinadvancedapp.SampleSnippets.runTask
import com.dhruva.kotlinadvancedapp.SampleSnippets.taskA1
import com.dhruva.kotlinadvancedapp.SampleSnippets.taskA2
import org.junit.Test

class SampleSnippetsTest {

    /*
    * The compiler replaces runTask with the println("Before"), task,
    * and println("After") code, creating (0) overhead objects.
    * */
    @Test
    fun testRunTask() {
        runTask { println("the inline task code execution!") }
    }

    /*
    * test function for noinline
    * */
    @Test
    fun testProcess() {
        process(
            action = { println("action") },
            logger = {}
        )
    }

    @Test
    fun testTaskA1(){
        taskA1()
    }

    @Test
    fun testTaskA2(){
        taskA2()
    }

    @Test
    fun testExtensionDollarSuffix(){
        println("dollar value : ${100.addDollarSuffix()}")
    }

    @Test
    fun testHigherOrderFunction(){
        val add = returnMeAddFun()
        val addVal = add(2,2)
        println("final value : $addVal")
    }
}