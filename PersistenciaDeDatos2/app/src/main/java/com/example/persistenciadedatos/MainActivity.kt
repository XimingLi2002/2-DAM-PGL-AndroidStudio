package com.example.persistenciadedatos

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.persistenciadedatos.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Guarda los componentes en una variable
        val fechaEditText = viewBinding.fechaEditText
        val textoEditText = viewBinding.textoEditText
        val grabarButton = viewBinding.grabarButton
        val recuperarButton = viewBinding.recuperarButton

        grabarButton.setOnClickListener {
            val fileName = fechaEditText.text.toString().replace('/', '-')
            try{
                val file = OutputStreamWriter(openFileOutput(fileName, Activity.MODE_PRIVATE))
                file.write(textoEditText.text.toString())
                file.flush()
                file.close()
                fechaEditText.setText("")
                textoEditText.setText("")
            }catch (e : IOException){}
        }

        recuperarButton.setOnClickListener{
            val fileName = fechaEditText.text.toString().replace('/', '-')
            if (fileList().contains(fileName)){
                try{
                    val file = InputStreamReader(openFileInput(fileName))
                    val buffer = BufferedReader(file)
                    var line = buffer.readLine()
                    val all = StringBuilder()
                    while (line != null){
                        all.append((line + "\n"))
                        line = buffer.readLine()
                    }
                    buffer.close()
                    file.close()
                    textoEditText.setText(all)
                }catch (e: IOException){}
            }else{
                Toast.makeText(this,"No existe ningun archivo con la fecha introducida", Toast.LENGTH_SHORT).show()
            }
        }

    }
}