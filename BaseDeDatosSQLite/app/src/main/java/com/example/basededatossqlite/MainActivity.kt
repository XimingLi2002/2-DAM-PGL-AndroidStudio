package com.example.basededatossqlite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.R
import androidx.appcompat.app.AlertDialog
import com.example.basededatossqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val codeInput = viewBinding.codeInput
        val nameInput = viewBinding.nameInput
        val tittleInput = viewBinding.tittleInput

        //Listener para el boton que ejecuta la sentencia insert (introduce un registro a nuestra bbdd)
        viewBinding.insertButton.setOnClickListener {
            val team = AdminSQLiteOpenHelper(this, "Teams", null, 1)
            val dataBase = team.writableDatabase

            //registro que se insertara
            val register = ContentValues()
            register.put("code", codeInput.text.toString().toInt())
            register.put("name", nameInput.text.toString())
            register.put("num_titles", tittleInput.text.toString().toInt())

            //insertamos el registro (Objeto ContentValues()) en la tabla
            dataBase.insert("Teams", null, register)
            dataBase.close()

            //Limpia los campos de texto
            codeInput.setText("")
            nameInput.setText("")
            tittleInput.setText("")
            showMessage("Registros guardados")
        }
        //Listener para el boton de busqueda, busca en el bbdd los datos del registro que contenga la primary key introducida
        viewBinding.findButton.setOnClickListener {
            val team = AdminSQLiteOpenHelper(this, "Teams", null, 1)
            val dataBase = team.writableDatabase

            //Realiza consulta y obtiene el resultado donde codigo es igual a lo que introducimos en el campo de texto
            val register = dataBase.rawQuery(
                "SELECT name,num_titles FROM Teams WHERE code=${
                    codeInput.text.toString().toInt()
                }", null
            )

            //Verifica si se ha recuperado algún resultado.
            if (register.moveToFirst()) {
                nameInput.setText(register.getString(0))
                tittleInput.setText(register.getInt(1).toString())
            } else {
                showMessage("El equipo no existe")
            }
            dataBase.close()
        }

        //MODIFICAR UN REGISTRO POR CODIGO
        viewBinding.modifierButton.setOnClickListener {
            if (codeInput.text.toString().isNotEmpty()) {
                val team = AdminSQLiteOpenHelper(this, "Teams", null, 1)
                val dataBase = team.writableDatabase

                //Registro con los valores que modificaremos.
                val newRegister = ContentValues()
                newRegister.put("name", nameInput.text.toString())
                newRegister.put("num_titles", tittleInput.text.toString().toInt())

                //Actualiza el registro donde la id es igual al que se introduce en el campo de texto
                val rowUpdate = dataBase.update(
                    "Teams",
                    newRegister,
                    "code=${codeInput.text.toString().toInt()}",
                    null
                )

                //Verifica si se ha actualizado correctamente.
                if (rowUpdate == 1) {
                    showMessage("Registro actualizado")
                } else {
                    showMessage("Registro no actualizado")
                }
                dataBase.close()
                //Limpia los campos de texto
                codeInput.setText("")
                nameInput.setText("")
                tittleInput.setText("")
            } else {
                showMessage("El código no puede estar vacío")
            }

        }
        //Elimina de nuestra bbdd un registro
        viewBinding.deleteButton.setOnClickListener {
            if (codeInput.text.toString().isNotEmpty()) {
                val team = AdminSQLiteOpenHelper(this, "Teams", null, 1)
                val dataBase = team.writableDatabase

                //Elimina de la bbdd un registro según su id.
                val deleteRegister = dataBase.delete("Teams", "code=${codeInput.text.toString().toInt()}", null)

                //Verifica si se ha eliminado correctamente.
                if (deleteRegister == 1) {
                    showMessage("Registro eliminado")
                } else {
                    showMessage("Registro no eliminado")
                }
                //Limpia los campos de texto
                codeInput.setText("")
                nameInput.setText("")
                tittleInput.setText("")
                dataBase.close()

            } else {
                showMessage("El campo código no puede estar vacío")
            }
        }



        //SPINNER Y EL BOTON ALL
        viewBinding.allButton.setOnClickListener {
            val team = AdminSQLiteOpenHelper(this, "Teams", null, 1)
            val dataBase = team.writableDatabase

            val teamsList = ArrayList<String>()
            //Ejecutamos y recuperamos los resultados de una consulta
            val register = dataBase.rawQuery("SELECT name FROM Teams", null)

            //Mientras haya registros, añadira a la lista el nombre del equipo.
            while (register.moveToNext()) {
                teamsList.add(register.getString(0))
            }

            //Creamos un adaptador para el Spinner
            val arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, teamsList)
            //Le pasamos el adaptador creado al Spinner
            viewBinding.spinner.adapter = arrayAdapter
            dataBase.close()
        }

        //Muestra una alerta con el item seleccionado del Spinner.
        viewBinding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val alertDialog = AlertDialog.Builder(this@MainActivity)
                    alertDialog.setMessage("Seleccionaste ${p0!!.getItemAtPosition(p2)}")
                    alertDialog.show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    //Muestra un mensaje en Toast
    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}