package com.example.repasout3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.repasout3.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        //Crea el menu a partir del fichero xml utilizando el inflate
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.camera -> {
                startActivity(Intent(this@MainActivity, Image::class.java))
                this.finish()
            }
            R.id.video -> {
                startActivity(Intent(this@MainActivity, Video::class.java))
                this.finish()
            }
            R.id.internet -> {
                startActivity(Intent(this@MainActivity, WebView::class.java))
                this.finish()
            }
            R.id.map -> {
                startActivity(Intent(this@MainActivity, Map::class.java))
                this.finish()
            }
            R.id.thread -> {
                startActivity(Intent(this@MainActivity, Threads::class.java))
                this.finish()
            }
            R.id.ddbb -> {
                startActivity(Intent(this@MainActivity, Firebase::class.java))
                this.finish()
            }
            R.id.soundReproducer -> {
                startActivity(Intent(this@MainActivity, SoundReproducer::class.java))
                this.finish()
            }


        }
        return true
    }


}