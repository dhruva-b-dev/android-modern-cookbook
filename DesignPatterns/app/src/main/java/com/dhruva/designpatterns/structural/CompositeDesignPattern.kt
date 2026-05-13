package com.dhruva.designpatterns.structural

/*
* Composite Design Pattern
* Structural Design pattern that allows you to treat individual object
* or group of objects uniformly.
* Composes objects into tree structures.
* e.g. ViewGroup and View hierarchy
* */

interface FileSystemItem {
    fun showDetails()
}

class File(private val name: String) : FileSystemItem {
    override fun showDetails() {
        println("File : $name")
    }
}

class Directory(private val name: String) : FileSystemItem {
    val fileSystems = mutableListOf<FileSystemItem>()
    fun add(fileSystemItem: FileSystemItem) = fileSystems.add(fileSystemItem)

    fun remove(fileSystemItem: FileSystemItem) = fileSystems.remove(fileSystemItem)

    override fun showDetails() {
        println("Directory : $name")
        //recursively show details of all children
        for (fileSystem in fileSystems) {
            fileSystem.showDetails()
        }
    }
}