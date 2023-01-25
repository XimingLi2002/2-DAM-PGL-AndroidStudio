package com.example.repasocomponentes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.repasocomponentes.databinding.ActivityMainBinding

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

        //Ahora se puede acceder a los componentes sin usar el findViewById
        //Guarda los componentes declarados
        val townshipInput = viewBinding.townshipInput
        val genderInput = viewBinding.genderInput
        var genderInputSelection = "" //Para usarlo luego en fragments
        val aboutMeButton = viewBinding.aboutMeButton
        val genderSelectionText = viewBinding.genderSelectionText



        //RELLENAR DATOS A UN SPINNER
        fillSpinner(townshipInput)



        //ABRIR NUEVA AVTIVIDAD DANDOLE A UN BOTON Y PASANDOLE DATOS PARA MOSTRARLO EN LA NUEVA ACTIVIDAD
        aboutMeButton.setOnClickListener {
            /*No era necesario pero para practicar lo de pasar datos*/
            val intent = Intent(this@MainActivity, AboutMe::class.java)
            //putExtra(clave, valor) la clave es lo que se necesitara para acceder al valor
            intent.putExtra("nombre", "Ximing")
            intent.putExtra("apellido", "Li")
            //inicia la actividad nueva
            startActivity(intent)
        }



        //LISTENER QUE MUESTRA UNA COSA U OTRA EN FUNCION DE LA OPCION ELEGIDA
        genderInput.setOnCheckedChangeListener{ radioGroup: RadioGroup, i: Int ->
            when(i){
                R.id.maleRadioButton -> {
                    genderSelectionText.text = "Has seleccionado masculino"
                    genderInputSelection = findViewById<RadioButton>(R.id.maleRadioButton).text.toString()
                }
                R.id.femaleRadioButton -> {
                    genderSelectionText.text = "Has seleccionado femenino"
                    genderInputSelection = findViewById<RadioButton>(R.id.femaleRadioButton).text.toString()
                }
                R.id.otherRadioButton -> {
                    genderSelectionText.text = "Has seleccionado no binario"
                    genderInputSelection = findViewById<RadioButton>(R.id.otherRadioButton).text.toString()
                }
            }
        }



        //LISTENER PARA SABER SI EL CHECKBOX ESTA SELECCIONADO O NO
        var resident = ""
        val residentInput = viewBinding.residentInput
        residentInput.setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
            val alert = AlertDialog.Builder(this).create()
            if (b) {
                alert.setMessage("Eres residente")
                resident = "Eres residente"
            }else{
                alert.setMessage("No eres residente")
                resident= "No eres residente"
            }
            alert.show()
        }



        //LISTENER PARA SABER EL CAMPO ELEGIDO DENTRO DEL SPINNER
        var selection = ""
        townshipInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //Obtenemos el item en concreto, lo ponemos entre comillas para que sea como String
                selection = "${p0!!.getItemAtPosition(p2)}"
                viewBinding.townshipSelectionText.text = selection
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }



        //LISTENER PARA EL BOTON QUE EJECUTARA EL FRAGMENTO Y MOSTRARA LOS DATOS QUE SE LE APSA
        val consultButton = viewBinding.consultButton
        consultButton.setOnClickListener{
            val fragment = ConsultFragment()
            val bundle = Bundle()
            //Lo pasé de uno en uno aunque se puede pasarlo en solo un put, si lo convierto primero en un string con los datos
            bundle.putString("dni", viewBinding.dniInput.text.toString())
            bundle.putString("name", viewBinding.nameInput.text.toString())
            bundle.putString("surname", viewBinding.surnameInput.text.toString())
            bundle.putString("gender", genderInputSelection)
            bundle.putString("township", selection)
            bundle.putString("resident", resident)
            fragment.arguments = bundle
            showFragment(fragment)
        }
    }
    //Muestra el fragmento en nuestro frame layout
    private fun showFragment(frag: Fragment) {
        //Empezamos la acción
        val fragment = supportFragmentManager.beginTransaction()

        //Reemplazamos el fragmento que está activo en el frame layout indicado como parametro1
        //por el fragmento que le pasemos por el parametro2
        fragment.replace(R.id.fragment_layout, frag)

        //Terminamos la acción
        fragment.commit()
    }
    private fun fillSpinner(townshipInput: Spinner) {
        //[FORMA 1]
        //Crea el ArrayAdapter a partir del array creado en strings.xml que tiene el nombre municipiosGC
        val municipiosGC = ArrayAdapter.createFromResource(this,R.array.municipiosGC,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        //Personaliza el aspecto del spinner utilizando un layout predefinido por Android para listas desplegables
        municipiosGC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        townshipInput.adapter = municipiosGC

        //[FORMA 2]
        //Define el Array con los valores que aparecerán en el spinner
        //val municipiosGC = arrayOf("ListaMunicipios")
        //val arrayAdapter = ArrayAdapter(this@MainActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, municipiosGC)
        //townshipInput.adapter = arrayAdapter
    }

}