package com.example.registrosrecy.modelos

data class Usuario(
    var codigo: Int,
    var nombre: String,
    var apellido: String,
    var foto: ByteArray
) :
    java.io.Serializable {

    companion object {
        var codigo_com: Int = 0
        fun getCodigo(): Int {
            codigo_com++
            return codigo_com
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Usuario

        if (codigo != other.codigo) return false

        return true
    }

    override fun hashCode(): Int {
        return codigo
    }

    override fun toString(): String {
        return "Usuario(codigo=$codigo, nombre='$nombre', apellido='$apellido', foto=${foto.contentToString()})"
    }
}
