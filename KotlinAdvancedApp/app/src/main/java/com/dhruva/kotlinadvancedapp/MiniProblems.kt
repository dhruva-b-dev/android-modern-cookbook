package com.dhruva.kotlinadvancedapp

/*
* This file contains mini technical coding problems
* */

/*
* 1. Remove duplicates from an array
* */
fun removeDuplicates(arrayList: ArrayList<String>): List<String>{
    println("after removing duplicates: ${arrayList.distinct()}")
    return arrayList.distinct()
}
