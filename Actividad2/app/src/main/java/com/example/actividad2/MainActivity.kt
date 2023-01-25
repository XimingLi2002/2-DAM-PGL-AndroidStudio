package com.example.actividad2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Recupera los objetos y los almacena en una variable
        val RG_ZonaEstadioGC = findViewById<RadioGroup>(R.id.RG_ZonaEstadioGC)
        val TV_Informacion = findViewById<TextView>(R.id.TV_Informacion)
        val CB_Abonado = findViewById<CheckBox>(R.id.CB_Abonado)

        //Listener del RadioGroup
        RG_ZonaEstadioGC.setOnCheckedChangeListener{radioGroup, i ->
            when (i) {
                R.id.RB_GradaNaciente -> {
                    TV_Informacion.setText("Has seleccionado Grada Naciente")
                }
                R.id.RB_GradaCurva -> {
                    TV_Informacion.setText("Has seleccionado Grada Curva")
                }
                R.id.RB_GradaSur -> {
                    TV_Informacion.setText("Has seleccionado Grada Sur")
                }
                R.id.RB_Tribuna -> {
                    TV_Informacion.setText("Has seleccionado Tribuna")
                }
            }
        }

        //Listener del CheckBox
        CB_Abonado.setOnCheckedChangeListener{compoundButton, b ->
            val alert = AlertDialog.Builder(this).create()
            if (b) {
                alert.setMessage("Eres abonado")
            }else{
                alert.setMessage("No eres abonado")
            }
            alert.show()
        }
    }
}