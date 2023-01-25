package com.example.examenpgl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examenpgl.databinding.ActivityContentBinding
import com.example.examenpgl.databinding.ActivityShowProveedorDataBinding

class ShowProveedorData : AppCompatActivity() {
    private lateinit var viewBinding: ActivityShowProveedorDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityShowProveedorDataBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Guardo los campos de textos de datos del proveedor en variables
        val codeProveedorData = viewBinding.codeProveedorData
        val nameProveedorData = viewBinding.nameProveedorData
        val adressProveedorData = viewBinding.adressProveedorData
        val emailProveedorData = viewBinding.emailProveedorData

        //Accede al dato que se habia enviado en ActivityContent
        val bundle = intent.extras
        val selected = bundle?.getString("selected")


        val proveedores = BBDD_Proveedores(this, "Proveedores", null, 1)
        val dataBase = proveedores.writableDatabase

        //Realizamos una consulta y recuperamos el resultado.
        val register = dataBase.rawQuery(
            "SELECT cod_proveedor,direccion, email FROM Proveedores WHERE nombre='${selected}'", null
        )

        //Verifica si se ha recuperado algún resultado.
        if (register.moveToFirst()) {
            codeProveedorData.setText(register.getInt(0).toString())
            nameProveedorData.setText(selected)
            adressProveedorData.setText(register.getString(1))
            emailProveedorData.setText(register.getString(2))
        }

        dataBase.close()



        //añade un listener para el boton
        viewBinding.backButton.setOnClickListener {
            //ejecuta de nuevo la otra aplicacion
            val intent = Intent(this@ShowProveedorData, ActivityContent::class.java)
            startActivity(intent)
        }
    }
}