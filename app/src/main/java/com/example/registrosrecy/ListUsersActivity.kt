package com.example.registrosrecy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registrosrecy.modelos.Contenedor
import com.example.registrosrecy.Adaptadores.MiAdaptadorRecycler
import com.example.registrosrecy.databinding.ActivityListUsersBinding

class ListUsersActivity : AppCompatActivity() {
    var contenedor = intent.getSerializableExtra("usuarios") as Contenedor
    lateinit var miRecyclerView : RecyclerView
    lateinit var binding: ActivityListUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("Alfredo", contenedor.usuarios.toString())

        miRecyclerView = binding.listaPersonajesRecycler as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        var miAdapter = MiAdaptadorRecycler(contenedor.usuarios, this)
        miRecyclerView.adapter = miAdapter
    }
}