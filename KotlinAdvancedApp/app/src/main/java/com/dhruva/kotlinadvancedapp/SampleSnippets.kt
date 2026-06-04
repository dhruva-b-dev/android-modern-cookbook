package com.dhruva.kotlinadvancedapp

object SampleSnippets {

    const val NAME = "kotlin"

    /*
    *  The compiler copies the function's body—and its
    * lambda—directly to the call site. This avoids creating
    * an object for the lambda, but allows "non-local returns"
    * (using return inside the lambda exits the entire outer function)
    */
    inline fun runTask(task: () -> Unit) {
        println("Before")
        task()
        println("After")
    }

    /*
    * What it does: When marking a function inline,
    * all passed lambdas are inlined by default.
    * Use noinline when you want to inline the main function,
    * but you need one specific lambda to remain a regular object
    * (e.g., to store it in a variable or pass it elsewhere)
    * */
    inline fun process(action: () -> Unit, noinline logger: () -> Unit) {
        action()
        val logTask = logger
    }

    /*
    * This function depicts what happens when crossinline is not used.
    * it shows how non-local returns works.
    * */
    inline fun doTaskA1(abc:()->Unit){
        abc()
    }

    fun taskA1(){
        println("before task A1")
        doTaskA1 {
            println("task A1")
            return
        }
        println("after task A1")
    }

    /*
    * This function depicts what happens when crossinline is used.
    * crossinline in Kotlin is used to avoid non-local returns.
    * */
    inline fun doTaskA2(crossinline abc:()->Unit){
        abc()
    }

    fun taskA2(){
        println("before task A2")
        doTaskA2 {
            println("task A2")
            return@doTaskA2 //it won't allow return keyword - because it will do non-local return
        }
        println("after task A2")
    }

    /*
    * Extension Function
    * */

    fun Int.addDollarSuffix():String{
        return "$this$"
    }

    /*
    * Below is an example of a higher-order function
    * for execution - check the unit test class for SampleSnippets
    * */

    fun add(a:Int, b:Int):Int{
        return a+b
    }

    fun returnMeAddFun() : ((Int,Int)->Int){
        return ::add
    }
}