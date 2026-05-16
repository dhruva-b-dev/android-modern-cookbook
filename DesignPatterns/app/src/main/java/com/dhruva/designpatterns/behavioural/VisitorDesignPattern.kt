package com.dhruva.designpatterns.behavioural

/*
* Visitor
* a behavioral pattern used to separate algorithms from the objects on which they operate
* */
//element interface
interface ViewElement {
    fun accept(visitor: ViewVisitor)
}

class Button(val id: String, val label: String) : ViewElement {
    override fun accept(visitor: ViewVisitor) {
        visitor.visit(this)
    }
}

class Image(val url: String) : ViewElement {
    override fun accept(visitor: ViewVisitor) {
        visitor.visit(this)
    }
}

interface ViewVisitor {
    fun visit(button: Button)
    fun visit(image: Image)
}

class AnalyticsVisitor() : ViewVisitor {
    override fun visit(button: Button) {
        println("analytics for button : ${button.id} and ${button.label}")
    }

    override fun visit(image: Image) {
        println("analytics for image : ${image.url}")
    }
}

