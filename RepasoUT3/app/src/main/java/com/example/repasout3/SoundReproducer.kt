package com.example.repasout3

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.repasout3.databinding.SoundReproducerBinding

class SoundReproducer : AppCompatActivity() {
    private lateinit var viewBinding: SoundReproducerBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = SoundReproducerBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var position = 0

        mediaPlayer = MediaPlayer.create(this@SoundReproducer, R.raw.song)

        viewBinding.playButton.setOnClickListener {
            mediaPlayer.start()
        }

        viewBinding.pauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                position = mediaPlayer.currentPosition
                mediaPlayer.pause()
            }
        }

        viewBinding.stopButton.setOnClickListener {
            mediaPlayer.pause()
            position = 0
            mediaPlayer.seekTo(0)
        }

        // Get the action bar and add a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Action bar back button click listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this@SoundReproducer, MainActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}