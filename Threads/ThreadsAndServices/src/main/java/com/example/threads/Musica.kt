package com.example.threads

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast

class Musica : Service() {
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        showMessage("Servicio creado")
        //mediaPlayer = MediaPlayer.create(applicationContext, "Musica aqui con R.raw.nombreMusica")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showMessage("Servicio iniciado")
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        showMessage("Servicio detenido")
        mediaPlayer.stop()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}