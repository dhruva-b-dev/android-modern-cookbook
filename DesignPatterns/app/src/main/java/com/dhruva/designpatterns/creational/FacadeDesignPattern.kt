package com.dhruva.designpatterns.creational

/*
* Facade Pattern
* Allows you to interact with the surface of the things without
* getting into complexities of the object creation.
* A simplified interface to a larger, more complex system.
*
* e.g. Retrofit interface, Android MediaPlayer
* val mediaPlayer = MediaPlayer().apply {
    setDataSource("audio_file.mp3")
    prepare()
    start()
}
* */
class Projector {
    fun on() {
        println("Projector On")
    }

    fun setMode(value: String) {
        println("Projector Mode set : $value")
    }
}

class SoundSystem {
    fun on() {
        println("Sound System On")
    }

    fun setVolume(value: Int) {
        println("Volume set to : $value")
    }
}

class Lights {
    fun dim(value: Int) {
        println("Lights set to dim : $value")
    }
}

class HomeTheaterFacade(
    private val projector: Projector,
    private val soundSystem: SoundSystem,
    private val lights: Lights
) {
    fun watchMovie(movie: String) {
        println("Starting watching movie....$movie")
        projector.on()
        lights.dim(10)
        projector.setMode("Cinema")
        soundSystem.on()
        soundSystem.setVolume(50)
        println("Home Theater setup done!")
    }
}

