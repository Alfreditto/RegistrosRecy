package com.example.registrosrecy.modelos

import android.graphics.Bitmap

data class Usuario(var nombre: String, var apellido: String, var foto: Bitmap) :
    java.io.Serializable {
    override fun toString(): String {
        return "Usuario(nombre='$nombre', apellido='$apellido', foto=$foto)"
    }
}
