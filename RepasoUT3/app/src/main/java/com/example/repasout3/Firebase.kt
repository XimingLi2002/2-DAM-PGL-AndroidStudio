package com.example.repasout3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.example.repasout3.databinding.FirebaseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class Articulos(
    val codigoArticulo: String,
    val nombreArticulo: String,
    val pais: String,
    val precio: Float
) {
    override fun toString(): String {
        return "Articulos(codigoArticulo='$codigoArticulo',nombreArticulo='$nombreArticulo',pais='$pais',precio='$precio')"
    }
}

class Firebase : AppCompatActivity() {

    //BBDD
    val BBDD = FirebaseFirestore.getInstance()

    //Coleccion con la que se va a trabajar
    val articles = BBDD.collection("Articulos")

    private lateinit var viewBinding: FirebaseBinding

    private lateinit var dbArticleCodeInput: EditText
    private lateinit var dbArticleNameInput: EditText
    private lateinit var dbArticleCountryInput: EditText
    private lateinit var dbArticlePriceInput: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = FirebaseBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        dbArticleCodeInput = viewBinding.dbArticleCodeInput
        dbArticleNameInput = viewBinding.dbArticleNameInput
        dbArticleCountryInput = viewBinding.dbArticleCountryInput
        dbArticlePriceInput = viewBinding.dbArticlePriceInput


        viewBinding.dbInsertButton.setOnClickListener {
            insertar()
        }
        viewBinding.dbSearchButton.setOnClickListener {
            leer()
        }
        viewBinding.dbDeleteButton.setOnClickListener {
            borrar()
        }
        viewBinding.dbUpdateButton.setOnClickListener {
            actualizar()
        }

        // Get the action bar and add a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Action bar back button click listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this@Firebase, MainActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    fun insertar() {
        //Crea un objeto Articulo con los datos introducidos en los diferentes camponentes
        val article = Articulos(
            dbArticleCodeInput.text.toString(),
            dbArticleNameInput.text.toString(),
            dbArticleCountryInput.text.toString(),
            dbArticlePriceInput.text.toString().toFloat()
        )
        //Almacena en la coleccion por el codigo del artículo para facilitar la búsqueda y
        //que Firestore no de un valor alteario, es decir es una especie de 'PRIMARY KEY'
        articles.document(article.codigoArticulo).set(article)
        clearInputs()
        showMessage("Registro insertado")
    }

    fun leer() {
        //Recupera la coleccion poro CiF y lo carga en los editText
        articles.document(dbArticleCodeInput.text.toString()).get().addOnSuccessListener {
            if (it.exists()) {
                dbArticleCodeInput.setText(it.get("codigoArticulo").toString())
                dbArticleNameInput.setText(it.get("nombreArticulo").toString())
                dbArticleCountryInput.setText(it.get("pais").toString())
                dbArticlePriceInput.setText(it.get("precio").toString())
                showMessage("Datos recuperados")
            } else {
                showMessage("Recuperación fallida")
            }
        }
    }

    fun borrar() {
        //Busca un articulo por su codigo y lo elimina
        articles.document(dbArticleCodeInput.text.toString()).get().addOnSuccessListener {
            if (it.exists()) {
                it.reference.delete()
                showMessage("Artíulo eliminado")
            } else {
                showMessage("Eliminación fallida")
            }
        }
    }

    fun actualizar() {
        //Actualiza un articulo
        articles.document(dbArticleCodeInput.text.toString()).set(
            hashMapOf(
                "codigoArticulo" to dbArticleCodeInput.text.toString(),
                "nombreArticulo" to dbArticleNameInput.text.toString(),
                "pais" to dbArticleCountryInput.text.toString(),
                "precio" to dbArticlePriceInput.text.toString().toFloat()
            )
        )
        showMessage("Datos actualizados")
    }

    fun clearInputs(){
        dbArticleCodeInput.setText("")
        dbArticleNameInput.setText("")
        dbArticleCountryInput.setText("")
        dbArticlePriceInput.setText("")
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

