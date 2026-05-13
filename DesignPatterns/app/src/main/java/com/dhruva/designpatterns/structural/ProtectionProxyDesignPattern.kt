package com.dhruva.designpatterns.structural

/*
* Protection-Proxy
* Is used to provide surrogate or placeholder object which references an underlying object.
* It provides restricted access.
*
* e.g. File Access or network calls
* */

interface Feature {
    fun open()
}

class PremiumFeature : Feature {
    override fun open() {
        println("Access granted: Welcome to Premium app dashboard!")
    }
}

class FeatureProxy(private val user: User) : Feature {
    //lazy ensures realFeature only initialized if access is granted.
    private val realFeature by lazy { PremiumFeature() }
    override fun open() {
        if (user.isSubscribed) {
            realFeature.open()
        } else {
            println("Access denied!Please upgrade your subscription!")
        }
    }
}

data class User(val name: String, val isSubscribed: Boolean)