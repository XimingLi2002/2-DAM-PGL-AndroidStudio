package com.example.redwebandsounds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.redwebandsounds.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val bundle = intent.extras
        val webUrl:String? = bundle?.getString("url")

        viewBinding.webView.webViewClient = WebViewClient()
        viewBinding.webView.loadUrl(webUrl!!)

        viewBinding.backButton.setOnClickListener {
            val webIntent = Intent(this@WebViewActivity,MainActivity::class.java)
            startActivity(webIntent)

            this.finish()
        }
    }
}