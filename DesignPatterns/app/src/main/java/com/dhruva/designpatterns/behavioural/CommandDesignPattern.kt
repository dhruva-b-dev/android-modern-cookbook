package com.dhruva.designpatterns.behavioural

/*
* Command Design Pattern
* Command design pattern can be implemented to encapsulate a request
* as an Object, thus allowing us to parameterize and execute the requests
* at a later point in time.
* Beneficial in cases where we need to queue, log or undo operations.
* e.g. Button clicks
* */

interface Log {
    fun execute()
}

class PrintLog(private val message: String) : Log {
    override fun execute() {
        println(message)
    }
}

class LogsInvoker() {
    val logs = mutableListOf<Log>()

    fun addLog(log: Log) {
        logs.add(log)
    }

    fun executeLogs() {
        logs.forEach {
            it.execute()
        }
        logs.clear()
    }
}
