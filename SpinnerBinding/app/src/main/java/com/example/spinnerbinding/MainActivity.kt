package com.example.spinnerbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.spinnerbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Declaro la variable del tipo ActivityMainBinding
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inicio la variable declarado anteriormente
        binding = ActivityMainBinding.inflate(layoutInflater)
        //Muestra la vista raiz
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        //Ahora podemos acceder a los componentes sin usar el findViewById
        //Guardamos los componentes declarados
        val tvTittle = binding.tvTittle
        val spinner = binding.spinner
        val button = binding.button
        val tvResult = binding.tvResult

        //[FORMA 1]
        //Creo el ArrayAdapter a partir del array creado en strings.xml
        val municipiosGC = ArrayAdapter.createFromResource(this,R.array.municipiosGC,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        //Personaliza el aspecto del spinner utilizando un layout predefinido por Android para listas desplegables
        municipiosGC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = municipiosGC

        //[FORMA 2]
        //Define el Array con los valores que aparecerán en el spinner
        //dentro del parentesis se pondria ("valor1","valor2", ...)
        //val municipiosGC = arrayOf("ListaMunicipios")
        //val arrayAdapter = ArrayAdapter(this@MainActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, municipiosGC)
        //spinner.adapter = arrayAdapter

        //Añade los eventos al spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                tvResult.setText("No has seleccionado ningún municipio")
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                tvResult.setText("Has seleccionado ${p0!!.getItemAtPosition(p2)}")
            }
        }
    }
}