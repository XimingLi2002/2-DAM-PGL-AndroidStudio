package com.example.redwebandsounds

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.redwebandsounds.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.checkConnButton.setOnClickListener {
            if (checkNet(this)) {
                val type = connectionType(this)
                //showMessage("Conectado a Internet mediante ${type}")
            } else {
                //showMessage("No estas conectado a Internet")
            }
        }


        viewBinding.navButton.setOnClickListener {
            val url = viewBinding.pageUrlEditT.text.toString()

            val webIntent = Intent(this@MainActivity, WebViewActivity::class.java)
            webIntent.putExtra("url", url)
            startActivity(webIntent)
            this.finish()
        }



    }

    private fun checkNet(context: Context): Boolean {
        //Context.getSystemService(): Devuelve el identificador a un servicio de nivel de sistema por nombre. La clase del objeto devuelto varía según el nombre solicitado.
        //CONNECTIVITY_SERVICE: servicio de conectividad
        //ConnectivityManager:  maneja la gestión de las conexiones de red.
        val connectionManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        //Build.VERSION.SDK_INT: version SDK del software que se ejecuta actualmente en el dispositivo de hardware
        //Build.VERSION_CODES.M: Api 23
        //Dependiendo de la versión de Android hara una cosa u otra
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //activeNetworkInfo: devuelve detalles sobre la red de datos predeterminada actualmente activa
            val networkInfo = connectionManager.activeNetworkInfo as NetworkInfo
            return (networkInfo.isConnected)
        } else {
            //'?:': en caso de que sea nulo hara lo que esta despues de los ':'
            val activeNetwork = connectionManager.activeNetwork ?: return false
            val networkCapabilities =
                connectionManager.getNetworkCapabilities(activeNetwork) as NetworkCapabilities
            //comprueba si tiene la capacidad de acceder a internet
            return (networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET))
        }
    }

    private fun connectionType(context: Context): String {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            val networkInfo = connectivityManager.activeNetworkInfo as NetworkInfo
            return when (networkInfo.type) {
                ConnectivityManager.TYPE_MOBILE -> {
                    "Datos moviles"
                }
                ConnectivityManager.TYPE_WIFI -> {
                    "WIFI"
                }
                else -> {
                    ""
                }
            }
        } else {
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) as NetworkCapabilities
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return "Mobile data"
            else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return "wifi"
        }
        return "Internet OFF"
    }
}