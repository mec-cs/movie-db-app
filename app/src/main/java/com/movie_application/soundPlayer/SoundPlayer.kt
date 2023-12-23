package com.movie_application.soundPlayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class SoundPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    fun playSound(resourceId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, resourceId)
        mediaPlayer?.start()
    }

    fun playSoundFromUri(uri: Uri) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(context, uri)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }

    fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}