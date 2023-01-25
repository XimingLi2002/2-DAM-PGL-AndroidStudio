package com.example.examenpgl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.examenpgl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Declaro la variable del tipo ActivityMainBinding
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inicializa la variable creandolo y vinculandolo
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        //Muestra la vista raiz
        //setContentView(R.layout.activity_main)
        setContentView(viewBinding.root)

        //el usuario y contraseña correcta
        val correctUser = "admin"
        val correctPassword = "Qwerty1234"

        //Para pasar a otra aplicacion
        val intent = Intent(this@MainActivity, ActivityContent::class.java)

        //Almacena el boton de acceder a la aplicacion saludo
        val joinButton = viewBinding.joinButton

        //le da un listener
        joinButton.setOnClickListener() {
            //Pilla lo que se introdujo en los campos de texto y los almacena
            val userInput = viewBinding.userInput.text.toString()
            val passwordInput = viewBinding.passwordInput.text.toString()
            //comprueba si lo que se introdujo en los campos de texto corresponden con lo declarado al principio
            if (userInput == correctUser && passwordInput == correctPassword){
                //inicia otra aplicacion
                startActivity(intent)
            }else{
                showToastMessage("Usuario o contrseña incorrecta")
            }
        }
    }
    //Muestra un mensaje en Toast
    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}