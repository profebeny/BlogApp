package com.people4business.blogapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.people4business.blogapp.R

class Entrada(titulo: String?="SE", autor: String?="SE", contenido: String?="SE", fechaHora:String?="SE") : DialogFragment() {

    val titulo = titulo
    val autor = autor
    val contenido = contenido
    val fechaHora = fechaHora

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.df_entrada,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtTitulo = view.findViewById<TextView>(R.id.txtTitulo)
        val txtAutor = view.findViewById<TextView>(R.id.txtAutor)
        val txtContenido = view.findViewById<TextView>(R.id.txtContenido)
        val txtfechaHora = view.findViewById<TextView>(R.id.txtfechaHora)

        txtTitulo.text = titulo
        txtAutor.text = autor
        txtContenido.text = contenido
        txtfechaHora.text = fechaHora


    }
}