package com.example.examenpgl

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Clase de nuestra base de datos SQLite.
class BBDD_Proveedores(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(p0: SQLiteDatabase?) {
        //En caso de que no exista, crea una tabla nueva.
        p0!!.execSQL("CREATE TABLE Proveedores(cod_proveedor int primary key,nombre text,direccion text, email text)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}