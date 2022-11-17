package com.example.registrosrecy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.registrosrecy.auxiliar.Conexion
import com.example.registrosrecy.modelos.Contenedor
import com.example.registrosrecy.modelos.Usuario
import com.example.registrosrecy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var contenedor: Contenedor = Contenedor()
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val returnString = data?.getSerializableExtra("usuarioReg")
                contenedor.agregarUsuario(returnString as Usuario)
            }
        }
            val reultadoListar =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
                    val data: Intent? = result.data
                    val returnString = data?.getSerializableExtra("listaUsu")
                    contenedor.usuarios = returnString as ArrayList<Usuario>
                }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cargarBBDD()

        binding.btnReg.setOnClickListener {
            val intento: Intent = Intent(this, RegistroActivity::class.java)
            resultLauncher.launch(intento)
        }

        binding.btnListar.setOnClickListener {
            val intento: Intent = Intent(this, ListUsersActivity::class.java)
            intento.putExtra("usuarios", contenedor)
            Log.e("Alfredo", contenedor.usuarios.size.toString(), )
            reultadoListar.launch(intento)
        }
    }

    private fun cargarBBDD() {
        contenedor.usuarios.clear()
        var usuariosSQL = Conexion.obtenerusuarios(this)
        if (usuariosSQL.isNotEmpty()) {
            contenedor.usuarios = usuariosSQL
            Usuario.codigo_com = usuariosSQL.last().codigo
        }
    }
}
