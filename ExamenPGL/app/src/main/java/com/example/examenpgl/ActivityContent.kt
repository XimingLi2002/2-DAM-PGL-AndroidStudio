package com.example.examenpgl

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.R
import com.example.examenpgl.databinding.ActivityContentBinding

class ActivityContent : AppCompatActivity() {

    private lateinit var viewBinding: ActivityContentBinding

    //Guardo los campos de textos en variables
    private lateinit var codeInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var adressInput: EditText
    private lateinit var emailInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //SPINNER error
        var spinnerErrorController = true


        //Guardo los campos de textos en variables
        codeInput = viewBinding.codeInput
        nameInput = viewBinding.nameInput
        adressInput = viewBinding.adressInput
        emailInput = viewBinding.emailInput

        //Guardo los botones en variables
        val insertButton = viewBinding.insertButton


        //Listener para el boton que ejecuta la sentencia insert (introduce un registro a nuestra bbdd)
        insertButton.setOnClickListener {
            if (codeInput.text.toString().isNotEmpty() && nameInput.text.toString().isNotEmpty()) {
                //Instancia de la base de datos.
                val proveedores = BBDD_Proveedores(this, "Proveedores", null, 1)

                //Hace que la bbdd sea de lectura y escritura.
                val dataBase = proveedores.writableDatabase

                //Registro con los valores que insertamos
                val register = ContentValues()
                register.put("cod_proveedor", codeInput.text.toString().toInt())
                register.put("nombre", nameInput.text.toString())
                register.put("direccion", adressInput.text.toString())
                register.put("email", emailInput.text.toString())

                //insertamos el registro (Objeto ContentValues()) en la tabla
                dataBase.insert("Proveedores", null, register)
                dataBase.close()

                //Limpia los campos de texto
                clearInputs()

                showToast("Datos insertados")
            } else {
                showToast("No puedes dejar el codigo o el nombre vacio")
            }
        }

        //MODIFICAR UN REGISTRO POR CODIGO
        viewBinding.modifierButton.setOnClickListener {
            if (codeInput.text.toString().isNotEmpty()) {
                val proveedores = BBDD_Proveedores(this, "Proveedores", null, 1)
                val dataBase = proveedores.writableDatabase

                //Registro con los valores que modificaremos.
                val newRegister = ContentValues()
                newRegister.put("cod_proveedor", codeInput.text.toString().toInt())
                newRegister.put("nombre", nameInput.text.toString())
                newRegister.put("direccion", adressInput.text.toString())
                newRegister.put("email", emailInput.text.toString())

                //Actualizamos dicho registro con los nuevos datos según su id.
                val rowUpdate = dataBase.update(
                    "Proveedores",
                    newRegister,
                    "cod_proveedor=${codeInput.text.toString().toInt()}",
                    null
                )

                //Verifica si se ha actualizado correctamente.
                if (rowUpdate == 1) {
                    showToast("Registro actualizado")
                } else {
                    showToast("Registro no actualizado")
                }
                dataBase.close()
                //Limpia los campos de texto
                clearInputs()
            } else {
                showToast("El código no puede estar vacío")
            }

        }


        //SPINNER Y EL BOTON ACTUALIZAR SPINNER
        viewBinding.refreshSpinnerButton.setOnClickListener {
            val proveedores = BBDD_Proveedores(this, "Proveedores", null, 1)
            val dataBase = proveedores.writableDatabase

            val proveedoresList = ArrayList<String>()
            //Ejecutamos y recuperamos los resultados de una consulta
            val register = dataBase.rawQuery("SELECT nombre FROM Proveedores", null)

            proveedoresList.add("")
            //Mientras haya registros, añadira a la lista los nombres de los proveedores
            while (register.moveToNext()) {
                proveedoresList.add(register.getString(0))
            }

            //Creamos un adaptador para el Spinner
            val arrayAdapter =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, proveedoresList)
            //Le pasamos el adaptador creado al Spinner
            viewBinding.spinner.adapter = arrayAdapter
            dataBase.close()

            spinnerErrorController = true
        }


        //Muestra una alerta con el item seleccionado del Spinner.
        viewBinding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (!spinnerErrorController) {
                        //Para pasar a otra aplicacion
                        val intent = Intent(this@ActivityContent, ShowProveedorData::class.java)
                        //Pilla lo que ha seleccionado
                        val selected = p0!!.getItemAtPosition(p2).toString()
                        //pasa el valor a otra aplicacion
                        intent.putExtra("selected", selected)
                        //inicia otra aplicacion
                        startActivity(intent)
                    }else{
                        spinnerErrorController = false
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }


    }

    //Muestra un mensaje en Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    //Limpia los campos de texto
    private fun clearInputs() {
        codeInput.setText("")
        nameInput.setText("")
        adressInput.setText("")
        emailInput.setText("")
    }
}