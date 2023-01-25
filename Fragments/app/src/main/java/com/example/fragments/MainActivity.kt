package com.example.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        //Creo el ArrayAdapter a partir del array creado en strings.xml
        val municipiosGC = ArrayAdapter.createFromResource(this,R.array.municipiosGC,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        //Personaliza el aspecto del spinner utilizando un layout predefinido por Android para listas desplegables
        municipiosGC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = municipiosGC

        //Accede a los botones
        val buttonFragment1 = findViewById<Button>(R.id.button1)
        val buttonFragment2 = findViewById<Button>(R.id.button2)

        var text: String? = null
        var activeFragment : Int = 1
        //Listener que se ejecutará cada vez que se seleccione un valor dentro del spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                text = parent?.getItemAtPosition(position).toString()
                val fragment : Fragment = if (activeFragment == 1) {
                    BlankFragment1()
                }else{
                    BlankFragment2()
                }
                val dato = Bundle()
                dato.putString("municipio", text)
                fragment.arguments = dato
                showFragment(fragment)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        buttonFragment1.setOnClickListener {
            activeFragment = 1
            val fragment1 = BlankFragment1()
            val dato = Bundle()
            dato.putString("municipio", text)
            fragment1.arguments = dato
            showFragment(fragment1)
        }

        buttonFragment2.setOnClickListener {
            activeFragment = 2
            val fragment2 = BlankFragment2()
            val dato = Bundle()
            dato.putString("municipio", text)
            fragment2.arguments = dato
            showFragment(fragment2)
        }
    }
    //Funcion que muestra el fragmento mostrando el que se le pasa por parámetro
    fun showFragment(frag: Fragment){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_main, frag)
        fragment.commit()
    }
}