package com.example.onequiztorulethemalllotredition.helpers

import android.content.Context
import android.media.MediaPlayer

object MusicManager {
    private var mediaPlayer: MediaPlayer? = null
    private var currentPosition: Int = 0

    fun initialize(context: Context, audioResId: Int) {
        mediaPlayer = MediaPlayer.create(context, audioResId).apply {
            isLooping = true
        }
    }

    fun play() {
        mediaPlayer?.seekTo(currentPosition)
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.let {
            currentPosition = it.currentPosition
            it.pause()
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun changeTrack(context: Context, newAudioResId: Int) {
        release()
        initialize(context, newAudioResId)
        play()
    }
}