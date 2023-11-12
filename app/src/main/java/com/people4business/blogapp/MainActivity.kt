package com.people4business.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.people4business.blogapp.databinding.ActivityMainBinding
import com.people4business.blogapp.fragments.Entrada
import com.people4business.blogapp.fragments.NuevaEntrada
import com.people4business.blogapp.utilidades.Funciones

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    val funciones = Funciones()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabNuevaEntrada.setOnClickListener{

            if (funciones.isNetDisponible(this))
            {
                val nuevaEntrada =NuevaEntrada()
                nuevaEntrada.show(supportFragmentManager,"Nueva Entrada")
            }

            /*val entrada =Entrada("titulo","Beny Roman","Descripcion")
            entrada.show(supportFragmentManager,"Entrada")

             */
        }
    }
}