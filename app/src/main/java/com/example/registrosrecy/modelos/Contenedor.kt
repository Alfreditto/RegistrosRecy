package com.example.registrosrecy.modelos

class Contenedor(var usuarios: ArrayList<Usuario> = ArrayList()) : java.io.Serializable {
    fun agregarUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }
}
