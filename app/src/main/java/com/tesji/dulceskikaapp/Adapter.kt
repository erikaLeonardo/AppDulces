package com.tesji.dulceskikaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var datos: List<Dulces> = ArrayList()

    interface OnItemClickListener{
        fun onItemClick(tuModelo: Dulces)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        val txtid: TextView = itemView.findViewById(R.id.txtID)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecio)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val tuModelo = datos[position]
                    itemClickListener.onItemClick(tuModelo)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_dulces,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    fun setDatos(datos: List<Dulces>){
        this.datos = datos
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = datos[position]
        holder.txtid.text = item.id.toString()
        holder.txtNombre.text = item.nombre
        holder.txtPrecio.text = item.precio.toString()
    }
}