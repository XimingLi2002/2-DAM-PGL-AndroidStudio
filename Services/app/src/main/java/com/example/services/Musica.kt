package com.example.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast

class Musica : Service() {
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        showMessage("Servicio Creado")
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}