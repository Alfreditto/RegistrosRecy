package com.example.registrosrecy.Adaptadores

import android.content.Context
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.registrosrecy.R
import com.example.registrosrecy.modelos.Usuario
import com.bumptech.glide.Glide
import com.example.registrosrecy.auxiliar.Conexion
import java.io.File


class MiAdaptadorRecycler(personajes: ArrayList<Usuario>, var context: Context) :
    RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>() {

    companion object {
        //Esta variable estática nos será muy útil para saber cual está marcado o no.
        var seleccionado: Int = -1
        lateinit var personajes: ArrayList<Usuario>
        /*
        PAra marcar o desmarcar un elemento de la lista lo haremos diferente a una listView. En la listView el listener
        está en la activity por lo que podemos controlar desde fuera el valor de seleccionado y pasarlo al adapter, asociamos
        el adapter a la listview y resuelto.
        En las RecyclerView usamos para pintar cada elemento la función bind (ver código más abajo, en la clase ViewHolder).
        Esto se carga una vez, solo una vez, de ahí la eficiencia de las RecyclerView. Si queremos que el click que hagamos
        se vea reflejado debemos recargar la lista, para ello forzamos la recarga con el método: notifyDataSetChanged().
         */
    }


    /**
     * onBindViewHolder() se encarga de coger cada una de las posiciones de la lista de personajes y pasarlas a la clase
     * ViewHolder(clase interna, ver abajo) para que esta pinte todos los valores y active el evento onClick en cada uno.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = personajes.get(position)
        holder.bind(item, context, position, this)
    }

    /**
     *  Como su nombre indica lo que hará será devolvernos un objeto ViewHolder al cual le pasamos la celda que hemos creado.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return ViewHolder(layoutInflater.inflate(R.layout.item_card, parent, false), personajes)
    }

    /**
     * getItemCount() nos devuelve el tamaño de la lista, que lo necesita el RecyclerView.
     */
    override fun getItemCount(): Int {

        return personajes.size
    }


    //--------------------------------- Clase interna ViewHolder -----------------------------------
    /**
     * La clase ViewHolder. No es necesaria hacerla dentro del adapter, pero como van tan ligadas
     * se puede declarar aquí.
     */
    class ViewHolder(view: View, personajes: ArrayList<Usuario>) : RecyclerView.ViewHolder(view) {
        //Esto solo se asocia la primera vez que se llama a la clase, en el método onCreate de la clase que contiene a esta.
        //Por eso no hace falta que hagamos lo que hacíamos en el método getView de los adaptadores para las listsViews.
        //val nombrePersonaje = view.findViewById(R.id.txtNombre) as TextView
        //val tipoPersonaje = view.findViewById(R.id.txtTipo) as TextView
        //val avatar = view.findViewById(R.id.imgImagen) as ImageView

        //Como en el ejemplo general de las listas (ProbandoListas) vemos que se puede inflar cada elemento con una card o con un layout.
        val nombrePersonaje = view.findViewById(R.id.txtNombreCard) as TextView
        val tipoPersonaje = view.findViewById(R.id.txtRazaCard) as TextView
        val avatar = view.findViewById(R.id.imagePersonajeCard) as ImageView

        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(
            pers: Usuario,
            context: Context,
            pos: Int,
            miAdaptadorRecycler: MiAdaptadorRecycler
        ) {
            nombrePersonaje.text = pers.nombre
            tipoPersonaje.text = pers.apellido

            /*var fotoFichero =
                File(
                    context.getExternalFilesDir(null),
                    pers.nombre + pers.apellido
                )*/
            avatar.setImageBitmap(BitmapFactory.decodeByteArray(pers.foto, 0, pers.foto.size))

            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == seleccionado) {
                with(nombrePersonaje) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            } else {
                with(nombrePersonaje) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }
            itemView.setOnLongClickListener(View.OnLongClickListener {
                if (seleccionado == -1) {
                    seleccionarPersona(pos, miAdaptadorRecycler, context)
                }
                val builder = AlertDialog.Builder(context)

                with(builder) {
                    setTitle("Borrar")
                    setMessage("Seguro que quieres borrar")
                    //Otra forma es definir directamente aquí lo que se hace cuando se pulse.
                    setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
                        //Importante, si no esta seleccionado da error
                        val seleccionado1 = seleccionado
                        Conexion.delUsuario(context as AppCompatActivity, pers.codigo)
                        personajes.removeAt(seleccionado1)
                        Log.e("Alfredo", personajes.toString())
                        miAdaptadorRecycler.notifyItemRemoved(seleccionado)
                    }
                    setNegativeButton("No", ({ dialog: DialogInterface, which: Int ->

                    }))
                    show()
                }
                miAdaptadorRecycler.notifyDataSetChanged()
                true
            })
            //Se levanta una escucha para cada item. Si pulsamos el seleccionado pondremos la selección a -1, en otro caso será el nuevo sleccionado.
            itemView.setOnClickListener(View.OnClickListener {
                seleccionarPersona(pos, miAdaptadorRecycler, context)
            })

        }

        private fun seleccionarPersona(
            pos: Int,
            miAdaptadorRecycler: MiAdaptadorRecycler,
            context: Context
        ) {
            if (pos == seleccionado) {
                seleccionado = -1
            } else {
                seleccionado = pos
            }
            //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
            miAdaptadorRecycler.notifyDataSetChanged()

            Toast.makeText(
                context,
                "Valor seleccionado " + seleccionado.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

