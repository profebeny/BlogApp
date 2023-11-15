package com.people4business.blogapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.people4business.blogapp.R
import com.people4business.blogapp.data.EntradaBlog

class MyAdapter(private var EntradaList : ArrayList<EntradaBlog>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position : Int)
    }

    fun onItemClickListener(listener : onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.entrada_item,
        parent,false)
        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val Entradaitem = EntradaList[position]

        holder.tvTitulo.text = Entradaitem.titulo
        holder.tvAutor.text = Entradaitem.autor
        holder.tvPosicion.text = ""+position

        if (Entradaitem.contenido!!.length >= 70){
            holder.tvContenido.text = Entradaitem.contenido!!.substring(0,70)
        }else{
            holder.tvContenido.text = Entradaitem.contenido
        }

    }

    override fun getItemCount(): Int {

        return EntradaList.size
    }

    fun UpdateEntradas(userList : ArrayList<EntradaBlog>){
        this.EntradaList = userList
        notifyDataSetChanged()

    }


    class MyViewHolder(itemView : View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvTitulo : TextView = itemView.findViewById(R.id.tvTitulo)
        val tvAutor : TextView = itemView.findViewById(R.id.tvAutor)
        val tvContenido : TextView = itemView.findViewById(R.id.tvContenido)
        val tvPosicion : TextView = itemView.findViewById(R.id.tvPosicion)
        init {
            itemView.setOnClickListener(){
                listener.onItemClick(adapterPosition)
            }
        }

    }

}