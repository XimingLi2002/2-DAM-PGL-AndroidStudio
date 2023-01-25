package com.example.imageandvideo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imageandvideo.databinding.ActivityImageBinding
import com.example.imageandvideo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.cameraButton.setOnClickListener{
            val intent = Intent(this@MainActivity, Image::class.java)
            startActivity(intent)
            this.finish()
        }

        viewBinding.videoButton.setOnClickListener{
            val intent = Intent(this@MainActivity, Video::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}