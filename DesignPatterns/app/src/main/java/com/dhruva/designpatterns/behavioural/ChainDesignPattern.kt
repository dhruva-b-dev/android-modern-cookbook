package com.dhruva.designpatterns.behavioural

/*
* Chain of Responsibility Design Pattern
* The Chain of Responsibility design pattern enables an
* object to delegate a request to a chain of potential
* handlers without specifying the exact handler.
* */

data class Request(val data: String)

// Define the abstract Handler class
abstract class Handler(private val successor: Handler? = null) {

    abstract fun handleRequest(request: Request)

    // Passes the request to the next handler in the chain if there is one
    fun passToNext(request: Request) {
        successor?.handleRequest(request)
    }
}

//concrete handlers
class CustomerService(private val successor: Handler? = null) : Handler(successor) {
    override fun handleRequest(request: Request) {
        if (request.data == "Complaint") {
            println("Customer Service handling complaint.")
        } else {
            passToNext(request)
        }
    }
}

//concrete handlers
class Supervisor(private val successor: Handler? = null) : Handler(successor) {
    override fun handleRequest(request: Request) {
        if (request.data == "Escalate to Supervisor") {
            println("Supervisor handling complaint.")
        } else {
            passToNext(request)
        }
    }
}

class Manager(private val successor: Handler? = null) : Handler(successor) {
    override fun handleRequest(request: Request) {
        if (request.data == "Escalate to Manager") {
            println("Manager handling complaint.")
        } else {
            println("Request cannot be handled by anyone in the organization.")
        }
    }
}