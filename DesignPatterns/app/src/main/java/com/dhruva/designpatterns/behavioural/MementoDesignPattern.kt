package com.dhruva.designpatterns.behavioural

/*
* Memento Design Pattern
* The memento pattern is a software design pattern that
* provides the ability to restore an object to its previous state (undo via rollback).
* e.g. SavedStateHandle
* */

// 1. The Memento: Immutable state snapshot
data class TextStateMemento(val text: String)

// 2. The Originator: The object whose state changes and needs protection
class TextEditor {
    var text: String = ""

    fun createSnapshot(): TextStateMemento {
        return TextStateMemento(text)
    }

    fun restore(memento: TextStateMemento) {
        text = memento.text
    }
}

// 3. The Caretaker: Tracks historical states without modifying them
class HistoryTracker {
    private val mementoList = mutableListOf<TextStateMemento>()

    fun push(memento: TextStateMemento) {
        mementoList.add(memento)
    }

    fun pop(): TextStateMemento? {
        if (mementoList.isEmpty()) return null
        return mementoList.removeAt(mementoList.lastIndex)
    }

    val size: Int get() = mementoList.size
}

// 4. The Context Class: Coordinates operations, updates state, and requests undos
class EditorCoordinator {
    private val editor = TextEditor()
    private val history = HistoryTracker()

    fun writeText(newText: String) {
        // Save current state to history before making changes
        history.push(editor.createSnapshot())
        editor.text = newText
        println("Saved state. New text set to: \"${editor.text}\"")
    }

    fun triggerUndo() {
        val previousState = history.pop()
        if (previousState != null) {
            editor.restore(previousState)
            println("Undo executed. Restored text to: \"${editor.text}\"")
        } else {
            println("Undo failed: No history remaining.")
        }
    }

    fun printCurrentText() {
        println("Current Editor Text: \"${editor.text}\"")
    }
}
