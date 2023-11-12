package com.people4business.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.people4business.blogapp.databinding.ActivityMainBinding
import com.people4business.blogapp.fragments.Entrada
import com.people4business.blogapp.fragments.NuevaEntrada

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabNuevaEntrada.setOnClickListener{
            //val nuevaEntrada =NuevaEntrada()
            //nuevaEntrada.show(supportFragmentManager,"Nueva Entrada")

            val entrada =Entrada("titulo","Beny Roman","Descripcion")
            entrada.show(supportFragmentManager,"Entrada")
        }
    }
}