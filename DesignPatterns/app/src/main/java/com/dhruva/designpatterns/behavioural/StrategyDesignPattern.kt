package com.dhruva.designpatterns.behavioural

/*
* Strategy Design Pattern
*
* In strategy design pattern allows the client to choose
* from the family of algorithm at the runtime.
* It encapsulates each of the algorithm and make them
* interchangeable at runtime, based on specific requirement.
*
* e.g. Android uses it for handling of different UI states.
* val strategy = if (isTablet) TabletLayoutStrategy() else PhoneLayoutStrategy()
* strategy.applyLayout()
* */

interface SortStrategy {
    fun sort(dataset: MutableList<Int>)
}

class BubbleSort : SortStrategy {
    override fun sort(dataset: MutableList<Int>) {
        println("sort data using bubble sort!")
    }
}

class QuickSort : SortStrategy {
    override fun sort(dataset: MutableList<Int>) {
        println("sort data using quick sort!")
    }
}

class Sorter(private var strategy: SortStrategy) {
    fun setStrategy(strategy: SortStrategy) {
        this.strategy = strategy
    }

    fun sort(dataset: MutableList<Int>) {
        strategy.sort(dataset)
    }
}