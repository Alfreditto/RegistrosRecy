package com.example.registrosrecy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.registrosrecy.modelos.Contenedor
import com.example.registrosrecy.modelos.Usuario
import com.example.registrosrecy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var contenedor : Contenedor= Contenedor()
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val returnString = data?.getSerializableExtra("usuarioReg")
                contenedor.agregarUsuario(returnString as Usuario)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnReg.setOnClickListener {
            val intento: Intent = Intent(this, RegistroActivity::class.java)
            resultLauncher.launch(intento)
        }

        binding.btnListar.setOnClickListener {
            val intento: Intent = Intent(this, ListUsersActivity::class.java)
            intento.putExtra("usuarios", contenedor)
            startActivity(intento)
        }
    }
}