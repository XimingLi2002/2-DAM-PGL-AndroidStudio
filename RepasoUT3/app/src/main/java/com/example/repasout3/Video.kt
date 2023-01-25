package com.example.repasout3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.MediaController
import android.widget.VideoView
import com.example.repasout3.databinding.VideoBinding

class Video : AppCompatActivity() {
    private lateinit var viewBinding : VideoBinding
    private lateinit var video : VideoView
    companion object{
        val CAPTURE_VIDEO_CODE = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = VideoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.recordVideoButton.setOnClickListener {
            captureVideo()
        }

        // Get the action bar and add a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_VIDEO_CODE && resultCode == Activity.RESULT_OK) {
            val videoUri = data!!.data
            video.setVideoURI(videoUri)
            video.setMediaController(MediaController(this))
            video.requestFocus()
            video.start()
        }
    }
    // Action bar back button click listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this@Video, MainActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun captureVideo() {
        video = viewBinding.videoView
        val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(videoIntent, CAPTURE_VIDEO_CODE)
    }
}