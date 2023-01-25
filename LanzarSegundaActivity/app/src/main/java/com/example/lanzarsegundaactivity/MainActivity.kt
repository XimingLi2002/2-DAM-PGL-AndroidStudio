package com.example.lanzarsegundaactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //el usuario y contraseña correcta
        val correctUser = "admin"
        val correctPassword = "Dam1234"
        //Para pasar a otra aplicacion
        val intent = Intent(this@MainActivity, Saludo::class.java)

        //Almacena el boton de acceder a la aplicacion saludo
        val joinButton = findViewById<Button>(R.id.joinButton)

        //le da un listener
        joinButton.setOnClickListener() {
            //Pilla lo que se introdujo en los campos de texto y los almacena
            val userInput = findViewById<EditText>(R.id.userInput).text.toString()
            val passwordInput = findViewById<EditText>(R.id.passwordInput).text.toString()
            //comprueba si lo que se introdujo en los campos de texto corresponden con lo declarado al principio
            if (userInput == correctUser && passwordInput == correctPassword){
                //pasa valores a otra aplicacion
                intent.putExtra("usuario", userInput)
                //intent.putExtra("password", passwordInput)
                //inicia otra aplicacion
                startActivity(intent)
            }
        }
    }
}