package com.example.registrosrecy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registrosrecy.Adaptadores.MiAdaptadorRecycler
import com.example.registrosrecy.databinding.ActivityListUsersBinding
import com.example.registrosrecy.modelos.Contenedor

class ListUsersActivity : AppCompatActivity() {
    lateinit var miRecyclerView : RecyclerView
    lateinit var binding: ActivityListUsersBinding
lateinit var contenedor: Contenedor
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityListUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contenedor = intent.getSerializableExtra("usuarios") as Contenedor

        Log.e("Alfredo", contenedor.usuarios.toString())

        miRecyclerView = binding.listaPersonajesRecycler as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        var miAdapter = MiAdaptadorRecycler(contenedor.usuarios, this)
        miRecyclerView.adapter = miAdapter

    }
    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("listaUsu", contenedor.usuarios)
        setResult(RESULT_OK, intent)
        finish()
    }
}