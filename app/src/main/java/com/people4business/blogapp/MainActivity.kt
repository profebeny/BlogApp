package com.people4business.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.people4business.blogapp.databinding.ActivityMainBinding
import com.people4business.blogapp.fragments.NuevaEntrada

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabNuevaEntrada.setOnClickListener{
            Toast.makeText(this,"click",Toast.LENGTH_SHORT).show();
            val nuevaEntrada =NuevaEntrada()
            nuevaEntrada.show(supportFragmentManager,"Nueva Entrada")
        }
    }
}