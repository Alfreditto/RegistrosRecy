package com.example.registrosrecy.auxiliar

import com.example.registrosrecy.Conexion.AdminSQLIteConexion
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import com.example.registrosrecy.modelos.Usuario

object Conexion {
    var nombreBD = "usuarios.db3"

    fun cambiarBD(nombreBD: String) {
        Conexion.nombreBD = nombreBD
    }

    fun addUsuario(contexto: AppCompatActivity, p: Usuario) {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("codigo", p.codigo)
        registro.put("nombre", p.nombre)
        registro.put("apellido", p.apellido)
        registro.put("foto", p.foto)
        //registro.put(null)
        bd.insert("usuarios", null, registro)
        bd.close()
    }

    fun delUsuario(contexto: AppCompatActivity, codigo: String): Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("usuarios", "codigo='${codigo}'", null)
        bd.close()
        return cant
    }

    fun modUsuario(contexto: AppCompatActivity, codigo: Int, p: Usuario): Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("nombre", p.nombre)
        registro.put("apellido", p.apellido)
        val cant = bd.update("usuarios", registro, "codigo='${codigo}'", null)
        bd.close()
        return cant
    }

    fun buscarUsuario(contexto: AppCompatActivity, codigo: Int): Usuario? {
        var p: Usuario? = null
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select nombre,apellido from usuarios where codigo='${codigo}'",
            null
        )
        if (fila.moveToFirst()) {
            p = Usuario(codigo, fila.getString(0), fila.getString(1), fila.getBlob(2))
        }
        bd.close()
        return p
    }

    fun obtenerusuarios(contexto: AppCompatActivity): ArrayList<Usuario> {
        var usuarios: ArrayList<Usuario> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("select codigo,nombre,apellido, foto from usuarios", null)
        while (fila.moveToNext()) {
            var p: Usuario =
                Usuario(fila.getInt(0), fila.getString(1), fila.getString(2), fila.getBlob(3))
            usuarios.add(p)
        }
        bd.close()
        return usuarios
    }

}