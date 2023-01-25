package com.example.repasocomponentes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AboutMe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)

        //Accede al dato que se habia enviado en la MainActivity
        val bundle = intent.extras
        val nombre = bundle?.getString("nombre")
        val apellido = bundle?.getString("apellido")

        val aboutMe = findViewById<TextView>(R.id.aboutMe)
        aboutMe.text = "App realizada por: " + nombre + ", " + apellido

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener{
            val intent = Intent(this@AboutMe, MainActivity::class.java)
            startActivity(intent)
        }
    }
}