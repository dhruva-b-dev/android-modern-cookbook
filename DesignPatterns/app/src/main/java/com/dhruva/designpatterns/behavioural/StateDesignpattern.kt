package com.dhruva.designpatterns.behavioural

import android.content.Context

/*
* State
* State design pattern allows object to change its behavior
* when it's internal state is updated.
* e.g. this is often implemented using sealed classes
* or interfaces to represent different states and a context class to manage the current state.
* */

//define the state interface
sealed class PlayerState {
    abstract fun handlePlay(context: MusicPlayer)
}

//concrete states
object Playing : PlayerState() {
    override fun handlePlay(context: MusicPlayer) {
        println("Already playing, will pause now....")
        context.state = Paused
    }
}

object Paused : PlayerState(){
    override fun handlePlay(context: MusicPlayer) {
        println("Already paused, will start playing now...")
        context.state = Playing
    }
}

// 3. Context Class
class MusicPlayer {
    // Default starting state
    var state: PlayerState = Paused

    fun pressPlay() {
        state.handlePlay(this)
    }
}