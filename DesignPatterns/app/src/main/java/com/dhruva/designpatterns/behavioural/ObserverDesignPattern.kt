package com.dhruva.designpatterns.behavioural

/*
* Observer Design Pattern
* Establishes a one-to-many relationship between an object(Subject or an observable)
* maintains a list of dependents(subscribers,observers) and automatically notifies them
* of any state change.
*
* e.g. StateFlow, LiveData
* */

interface Observer {
    fun update(data: String)
}

interface Observable {
    fun register(observer: Observer)
    fun deRegister(observer: Observer)
    fun notifyChange(data: String)
}

class Chanel : Observable {
    private val observers = mutableListOf<Observer>()
    override fun register(observer: Observer) {
        observers.add(observer)
    }

    override fun deRegister(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyChange(data: String) {
        observers.forEach {
            it.update(data)
        }
    }

    fun setData(data: String) {
        notifyChange(data)
    }
}

class DataDisplay : Observer {
    override fun update(data: String) {
        println("New data updated : $data")
    }
}

