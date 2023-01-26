package com.example.repasout3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.repasout3.databinding.FirebaseAuthBinding
import com.google.firebase.auth.FirebaseAuth


class FirebaseAuth : AppCompatActivity() {

    private lateinit var viewBinding: FirebaseAuthBinding

    private lateinit var loginTV: TextView
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = FirebaseAuthBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        loginTV = viewBinding.loginTV
        emailInput = viewBinding.emailInput
        passwordInput = viewBinding.passwordInput
        loginButton = viewBinding.loginButton
        registerButton = viewBinding.registerButton



        firebaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            if (emailInput.text.isNotEmpty() && passwordInput.text.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(
                    emailInput.text.toString(),
                    passwordInput.text.toString()
                ).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        showMessage("Inicio de sesión correcto")
                        startActivity(Intent(this@FirebaseAuth, Firebase::class.java))
                        this.finish()
                    } else {
                        showMessage("Fallo de autentificación")
                    }
                }
            } else {
                showMessage("Debes completar ambos campos")
            }
        }

        registerButton.setOnClickListener{
            if (emailInput.text.isNotEmpty() && passwordInput.text.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(
                    emailInput.text.toString(),
                    passwordInput.text.toString()
                ).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        showMessage("Usuario creado correctamente")
                    } else {
                        showMessage("Error al crear el usuario")
                    }
                }
            } else {
                showMessage("Debes completar ambos campos")
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}