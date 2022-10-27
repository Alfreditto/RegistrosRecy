package com.example.registrosrecy.modelos

import com.example.registrosrecy.modelos.Usuario

class Contenedor(var usuarios: ArrayList<Usuario> = ArrayList()) : java.io.Serializable {
    fun agregarUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }
}
