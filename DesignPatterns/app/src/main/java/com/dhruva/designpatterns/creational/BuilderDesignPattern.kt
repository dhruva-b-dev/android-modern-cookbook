package com.dhruva.designpatterns.creational

/*
* Builder Design Pattern
* Creational design pattern, that allows creation of complex object, while
* allowing optional modification on a single object.
* e.g. AlertDialog.Builder, Notification.Builder
* */
class BuilderDesignPattern {
    class Car private constructor(
        val brand: String,
        val model: String,
        val year: Int,
        val hasSunroof: Boolean
    ) {
        fun print() {
            print("car : $brand $model $year $hasSunroof")
        }

        //nested builder class
        class Builder() {
            var brand: String = ""
            var model: String = ""
            var year: Int = 0
            var hasSunRoof: Boolean = false
            fun setBrand(brand: String) = apply { this.brand = brand }
            fun setModel(model: String) = apply { this.model = model }
            fun setYear(year: Int) = apply { this.year = year }
            fun setSunroof(hasSunroof: Boolean) = apply { this.hasSunRoof = hasSunroof }

            fun build() = Car(brand, model, year, hasSunRoof)
        }
    }
}