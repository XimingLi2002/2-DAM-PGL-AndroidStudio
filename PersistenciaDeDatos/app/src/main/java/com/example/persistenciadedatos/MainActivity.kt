package com.example.persistenciadedatos

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.persistenciadedatos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Guarda los componentes en una variable
        val emailEditText = viewBinding.emailEditText
        val datosEditText = viewBinding.datosEditText
        val grabarButton = viewBinding.grabarButton
        val recuperarButton = viewBinding.recuperarButton

        val preferences = getSharedPreferences("email_data", Context.MODE_PRIVATE)
        //Objeto editor que nos permite acceder a las preferencias
        val editor = preferences.edit()

        grabarButton.setOnClickListener {
            if (emailEditText.text.toString().trim() != "" && datosEditText.text.toString().trim() != ""){
                editor.putString(emailEditText.text.toString(), datosEditText.text.toString())
                //confirma la grabacion de los datos
                editor.apply()
                //Muestra una alerta de que los datos se han guardado
                Toast.makeText(this,"Datos guardados", Toast.LENGTH_SHORT).show()
                //Limpia los campos de texto
                emailEditText.setText("")
                datosEditText.setText("")
            }
        }

        recuperarButton.setOnClickListener{
            val data = preferences.getString(emailEditText.text.toString(), "")
            if (data != null){
                if (data.isEmpty()){
                    Toast.makeText(this,"No existe agenda para dicho email", Toast.LENGTH_SHORT).show()
                }else{
                    datosEditText.setText(data)
                }
            }
        }
    }
}