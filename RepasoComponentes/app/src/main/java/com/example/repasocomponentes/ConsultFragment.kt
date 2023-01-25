package com.example.repasocomponentes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

//Cambiamos el valor del argumento según el 'key' pasado por argumento
private const val ARG_PARAM1 = "dni"
private const val ARG_PARAM2 = "name"
private const val ARG_PARAM3 = "surname"
private const val ARG_PARAM4 = "gender"
private const val ARG_PARAM5 = "township"
private const val ARG_PARAM6 = "resident"


/**
 * A simple [Fragment] subclass.
 * Use the [ConsultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var dni: String? = null
    private var name: String? = null
    private var surname: String? = null
    private var gender: String? = null
    private var township: String? = null
    private var resident: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dni = it.getString(ARG_PARAM1)
            name = it.getString(ARG_PARAM2)
            surname = it.getString(ARG_PARAM3)
            gender = it.getString(ARG_PARAM4)
            township = it.getString(ARG_PARAM5)
            resident = it.getString(ARG_PARAM6)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_consult, container, false)

        //Busca el componente con su respectivo id dentro del view de nuestro fragment
        val fragmentTextV = fragment.findViewById<TextView>(R.id.fragmentTextV)

        //Listener del TextView
        fragmentTextV.setOnClickListener {
            //Mostramos un Toast
            Toast.makeText(
                activity,
                "Has hecho click en el Text View del fragmento",
                Toast.LENGTH_LONG
            ).show()
        }
        return fragment
    }

    //Al crearse el fragment, se cambia el texto del TextView por el texto que obtuvimos como
    //argumento del Bundle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.fragmentTextV).text =
            "Dni: ${dni}\nNombre: ${name}\nApellidos: ${surname}\nGénero: ${gender}\nMunicipio: ${township}\n¿Residente?: ${resident}"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConsultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConsultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}