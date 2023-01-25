package com.example.repasout3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.repasout3.databinding.ThreadsBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Threads : AppCompatActivity() {
    private lateinit var viewBinding: ThreadsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ThreadsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initListeners()

        // Get the action bar and add a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Action bar back button click listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this@Threads, MainActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initListeners() {
        viewBinding.startCountDownButton.setOnClickListener {
            if (viewBinding.timeInput.text.isNotBlank()) {

                val seconds = viewBinding.timeInput.text.toString().toInt()
                countDownCoroutine(seconds)

            } else {
                showMessage("No input detected")
            }
        }
    }

    /* Coroutine for countdown */
    @OptIn(DelicateCoroutinesApi::class)
    private fun countDownCoroutine(s: Int) {
        /* Execute the next code as IO Dispatcher, better performance because uses another thread */
        GlobalScope.launch(Dispatchers.IO) {
            var seconds = s

            while (seconds >= 0) {

                /* Use Dispatcher Main for code that interact with our view components */
                launch(Dispatchers.Main) {
                    viewBinding.timeView.text = seconds.toString()
                    viewBinding.startCountDownButton.isClickable = false
                }

                try {
                    Thread.sleep(1000)
                    seconds--
                } catch (_: InterruptedException) {
                }
            }

            /* Use Dispatcher Main for code that interact with our view components */
            launch(Dispatchers.Main) {
                viewBinding.startCountDownButton.isClickable = true
                showMessage("Timer finished!")
            }
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}