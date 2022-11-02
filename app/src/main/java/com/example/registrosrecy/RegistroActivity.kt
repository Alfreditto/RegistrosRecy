package com.example.registrosrecy

import com.example.registrosrecy.auxiliar.Conexion
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.registrosrecy.databinding.ActivityRegistroBinding
import com.example.registrosrecy.modelos.Usuario
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class RegistroActivity : AppCompatActivity() {
    private val cameraRequest = 1888
    lateinit var photo: Bitmap
    lateinit var photo_aux: ByteArray
    lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegVolver.setOnClickListener {
            val txtNombre = binding.txtNombre.text.toString()
            val txtApellido = binding.txtApellido.text.toString()
            val txtFoto = binding.imgUser.toString()
            if (txtNombre.isEmpty() || txtApellido.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                var usuario: Usuario = Usuario(
                    Usuario.getCodigo(), txtNombre, txtApellido, photo_aux
                )
                Conexion.addUsuario(this, usuario)

                Log.v("Usuario", usuario.toString())
                val intent = Intent()
                intent.putExtra("usuarioReg", usuario)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }
        binding.btnFoto.setOnClickListener {
            //check if i have permission to use the camera
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    cameraRequest
                )
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, cameraRequest)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == cameraRequest) {

                photo = data?.extras?.get("data") as Bitmap
                //Esto convierte el Bitmap a un ByteArray
                val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
                photo_aux = stream.toByteArray()
                binding.imgUser.setImageBitmap(photo)

                var fotoFichero =
                    File(
                        getExternalFilesDir(null),
                        binding.txtNombre.text.toString() + binding.txtApellido.text.toString()
                    )
                var uri = Uri.fromFile(fotoFichero)
                var fileOutStream = FileOutputStream(fotoFichero)
                photo.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
                fileOutStream.flush();
                fileOutStream.close();
            }
        } catch (e: java.lang.Exception) {
            Log.e("Fernando", e.toString())
        }
    }
}