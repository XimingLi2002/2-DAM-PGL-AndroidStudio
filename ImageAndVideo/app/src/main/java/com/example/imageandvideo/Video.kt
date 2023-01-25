package com.example.imageandvideo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.VideoView
import com.example.imageandvideo.databinding.ActivityVideoBinding

class Video : AppCompatActivity() {
    private lateinit var viewBinding : ActivityVideoBinding
    private lateinit var video : VideoView
    companion object{
        val CAPTURE_VIDEO_CODE = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.backButtonVideo.setOnClickListener {
            captureVideo()
        }
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

    private fun captureVideo() {
        video = viewBinding.videoView
        val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(videoIntent, CAPTURE_VIDEO_CODE)
    }
}