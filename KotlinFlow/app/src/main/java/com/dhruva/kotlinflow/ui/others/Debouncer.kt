package com.dhruva.kotlinflow.ui.others

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Debouncer(
    private val scope: CoroutineScope,
    private val waitTime:Long
) {
    private var job : Job? = null

    fun post(block:()->Unit){
        job?.cancel()
        job = scope.launch {
            delay(waitTime)
            block()
        }
    }
}

fun main() = runBlocking {
    val debouncer = Debouncer(scope = this, waitTime = 300L)
    debouncer.post { println("debounce test 1") }
    delay(100)
    debouncer.post { println("debounce test 2") }
    delay(400)
    debouncer.post { println("debounce test 3") }
    delay(200)
    debouncer.post { println("debounce test 4") }
    delay(500)
}

/*
Output:
debounce test 2
debounce test 4
*/