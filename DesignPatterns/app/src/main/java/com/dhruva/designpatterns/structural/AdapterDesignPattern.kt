package com.dhruva.designpatterns.structural

/*
* Adapter
* The pattern provides link between 2 otherwise incompatible types by
* wrapping "adaptee" with a class that supports the interface required by client.
*
* e.g. RecyclerView Adapter
* */

//target interface
interface Usb{
    fun connectWithUsb(){
        println("Connected with Usb!")
    }
}

//adaptee (The incompatible legacy class)
class MicroUsb {
    fun connectWithMicroUsb(){
        println("Connected with Micro Usb!")
    }
}

//Adapter (Wraps the Adaptee to match the Target)
class MicroUsbToUsbAdapter(private val microUsb: MicroUsb) : Usb{
    override fun connectWithUsb() {
        microUsb.connectWithMicroUsb()
    }
}