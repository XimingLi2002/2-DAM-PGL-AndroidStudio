package com.example.deletelater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = Firebase.auth
    }
    private fun sendEmailVerification(){
        val user = firebaseAuth.currentUser
        user!!.sendEmailVerification().addOnCompleteListener(this) {
            task ->
            if (task.isSuccessful){
                //Toast "Se envió un correo de verificación"
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null){
            //Si el usuario ha verificado su correo
            if (currentUser.isEmailVerified){
                reload()
            } else{
                //en caso contrario se tiene que enviar un correo de verificacion
                sendEmailVerification()
            }
        }
    }

    private fun reload() {
        val intent = Intent(this, /*Activity*/::class.java)
        this.startActivity(intent)
    }

}