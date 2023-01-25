package com.example.threads

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import com.example.threads.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMainBinding
    private lateinit var myProgressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myProgressBar = viewBinding.progressBar
        val toggleButton = viewBinding.toggleButton
        toggleButton.setOnClickListener{
            if (toggleButton.isChecked){
                startService(Intent(this@MainActivity, Musica::class.java))
            }else{
                stopService(Intent(this@MainActivity, Musica::class.java))
            }
        }
    }

    fun simulaTarea(){
        try{
            Thread.sleep(1000)
        }catch (e : InterruptedException){}
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        //Crea el menu a partir del fichero xml utilizando el inflate
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.sinhilos -> {
                sinhilos()
            }
            R.id.thread -> {
                conthread()
            }
            R.id.asynctask -> {
                conasynctask()
            }
            R.id.corrutinas -> {
                corrutinas()
            }
        }
        return true
    }
    fun sinhilos(){
        //Inicia el valor maximo y el valor de comienzo de la barra de progreso
        myProgressBar.max = 100
        myProgressBar.setProgress(0)

        //actualiza la barra de progreso
        for(i in 1 .. 10){
            simulaTarea()
            myProgressBar.incrementProgressBy(10)
        }
        showMessage("Tarea sin hilos finalizada.")
    }
    fun conthread(){
        Thread{
            //Accede al hilo principal para inicializar el valo maximo y el valor de comienzo de la barra de progreso
            runOnUiThread{
                myProgressBar.max = 100
                myProgressBar.setProgress(0)
            }

            for(i in 1 .. 10){
                //Accede al hilo principal para incrementar el progreso
                runOnUiThread{
                    myProgressBar.incrementProgressBy(10)
                }
                simulaTarea()
            }

            //Accede al hilo principal para comunicar que ha finalizado la tarea.
            runOnUiThread {
                showMessage("Tarea con Thread FINALIZADA.")
            }
        }.start()
    }
    fun conasynctask(){
        val tarea = TareaProgreso()
        tarea.execute()
    }
    fun corrutinas(){
        //Define la corrutina sobre la que se trabajara con el hilo de E/S
        GlobalScope.launch(Dispatchers.IO) {
            //Inicia la barra de progreso por eso ejecuta el codigo en el hilo principal
            launch(Dispatchers.Main){
                myProgressBar.max = 100
                myProgressBar.setProgress(0)
            }
            for (i in 1 .. 10){
                //Actualiza la barra de progreso (hilo principal)
                launch(Dispatchers.Main){
                    myProgressBar.incrementProgressBy(10)
                }
                simulaTarea()
            }
            launch(Dispatchers.Main) {
                //Mensaje informativo para saber ¿cuando ha terminado la tarea?
                showMessage("Tarea con Corrutinas FINALIZADA")
            }
        }
    }
    inner class TareaProgreso: AsyncTask<Void, Int, Void>(){
        override fun doInBackground(vararg p0: Void?): Void? {
            //Incrementamos el progreso
            for (i in 1 .. 10){
                publishProgress(10)
                simulaTarea()
            }
            return null
        }

        override fun onPreExecute() {
            myProgressBar.max = 100
            myProgressBar.setProgress(0)
        }

        override fun onProgressUpdate(vararg values: Int?) {
            myProgressBar.incrementProgressBy(values[0]!!)
        }

        override fun onPostExecute(result: Void?) {
            showMessage("TAREA CON ASYNCTASK finalizada")
        }

        override fun onCancelled() {
            showMessage("Tarea cancelada")
        }
    }
    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}