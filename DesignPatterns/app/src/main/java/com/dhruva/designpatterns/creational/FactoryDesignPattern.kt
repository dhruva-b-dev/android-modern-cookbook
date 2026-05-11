package com.dhruva.designpatterns.creational

import java.util.Locale
import java.util.Locale.getDefault

/*
* This is example of Factory Design Pattern.
* Factory takes care of object creation logic without specifying exact concrete class.
* E.g. when dealing with fragments in Android development,
* using the Factory Pattern is highly recommended.
* This is because during events like configuration changes or where
* the Android platform needs to recreate the fragment, keeping track of all
* the parameters within the fragment becomes challenging without a proper factory mechanism.
*
* E.g. Fragment, ViewModel Factory
* */

//Define the product interface
interface FileParser {
    fun parse(): String
}

//Create concrete products
class JSONFileParser : FileParser {
    override fun parse() = "Parsing data from a JSON file..."
}

//Create concrete products
class XmlFileParser : FileParser {
    override fun parse() = "Parsing data from a Xml file..."
}

//The Factory (using a companion object for idiomatic Kotlin)
class FactoryDesignPattern() {
    companion object {
        fun createParser(type: String): FileParser {
            return when (type.lowercase(getDefault())) {
                "json" -> JSONFileParser()
                "xml" -> XmlFileParser()
                else -> throw IllegalArgumentException("Invalid type!")
            }
        }
    }
}