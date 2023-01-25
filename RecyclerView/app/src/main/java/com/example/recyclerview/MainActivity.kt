package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Jugador(val nombre: String, val edad: Int)

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Accede al RecyclerView definido
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //Crea el array con de tamaño 30 utilizando una funcion lambda
        val datos = Array(30){i -> Jugador("Jugador $i", i)}

        //Creamos el adaptador
        val adaptador = AdaptadorJugadores(datos){
            Toast.makeText(this, "Has pulsado el -> ${it.nombre}", Toast.LENGTH_LONG).show()
        }

        //Especificamos el LayoutManager que vamos a utilizar en este caso LinearLayout vertical
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        recyclerView.layoutManager = GridLayoutManager(this,4)

        //Asociamos el RecyclerView el adaptador
        recyclerView.adapter = adaptador
    }
}

class AdaptadorJugadores(private val datos: Array<Jugador>, private val clickListener: (Jugador) -> Unit):
    RecyclerView.Adapter<AdaptadorJugadores.JugadoresViewHolder>() {
    class JugadoresViewHolder(val item: View) : RecyclerView.ViewHolder(item) {

        val nombre = item.findViewById<TextView>(R.id.nombre)
        val edad = item.findViewById<TextView>(R.id.edad)

        fun bindJugador(jugador: Jugador) {
            nombre.text = jugador.nombre
            edad.text = jugador.edad.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadoresViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.listitem_jugador,parent,false) as LinearLayout
        return JugadoresViewHolder(item)
    }

    override fun onBindViewHolder(holder: JugadoresViewHolder, position: Int) {
        val jugador = datos[position]
        holder.bindJugador(jugador)

        holder.item.setOnClickListener{clickListener(jugador)}
    }

    override fun getItemCount(): Int = datos.size
}