package com.example.lanzarsegundaactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class Saludo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saludo)

        //Accede al dato que se habia enviado en la MainActivity
        val bundle = intent.extras
        val usuario = bundle?.getString("usuario")
        //val password = bundle?.getString("password")
        //guarda los componentes en una variable
        val saludo = findViewById<TextView>(R.id.saludoTextView)
        val exitButton = findViewById<Button>(R.id.exitButton)
        //setea al text view un texto
        saludo.text = "Hola ${usuario}" //cuyo contraseña es: ${password}"

        //añade un listener para el boton
        exitButton.setOnClickListener{
            //ejecuta de nuevo la otra aplicacion
            val intent = Intent(this@Saludo, MainActivity::class.java)
            startActivity(intent)
        }

    }
}