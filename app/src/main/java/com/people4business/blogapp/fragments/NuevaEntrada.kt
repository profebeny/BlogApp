package com.people4business.blogapp.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.people4business.blogapp.R
import com.people4business.blogapp.data.EntradaBlog
import com.people4business.blogapp.data.EntradaBlogResponse
import com.people4business.blogapp.utilidades.Funciones
import com.people4business.blogapp.viewmodel.NuevaEntradaViewModel

class NuevaEntrada:DialogFragment(){

    lateinit var viewModel: NuevaEntradaViewModel
    val funciones = Funciones()
    lateinit var txtVDescripcion:TextView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.df_nueva_entrada,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        val btnPublicar = view.findViewById<TextView>(R.id.btnPublicar)
        val txtTituloPost = view.findViewById<TextView>(R.id.txtTituloPost)
        val txtTitulo = view.findViewById<TextView>(R.id.txtTitulo)
        txtVDescripcion = view.findViewById<TextView>(R.id.txtVDescripcion)
        val imgMicro = view.findViewById<ImageView>(R.id.imgMicro)

        imgMicro.setOnClickListener{
            displaySpeechRecognizer();
        }

        btnPublicar.setOnClickListener {
            if(validar(txtTituloPost.text.toString(),txtTitulo.text.toString(),txtVDescripcion.text.toString()))
            {
                if (funciones.isNetDisponible(view.context))
                {
                    createEntrada(txtTituloPost.text.toString(),txtTitulo.text.toString(),txtVDescripcion.text.toString())
                }
            }
        }
    }

    // Create an intent that can start the Speech Recognizer activity
    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        // This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, Companion.SPEECH_REQUEST_CODE)
    }

    private fun validar(titulo: String, autor: String, contenido: String): Boolean {
        if(titulo.equals("")){
            Toast.makeText(context, "Completa el título", Toast.LENGTH_LONG).show()
            return false
        }
        if(autor.equals("")){
            Toast.makeText(context, "Completa el autor", Toast.LENGTH_LONG).show()
            return false
        }
        if(contenido.equals("")){
            Toast.makeText(context, "Completa el contenido", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun createEntrada(titulo: String, autor: String, contenido: String) {
        val entradaBlog  = EntradaBlog(titulo,autor,contenido,funciones.dataTime())
        viewModel.createNewEntrada(entradaBlog)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(NuevaEntradaViewModel::class.java)
        viewModel.getCreateNewEntradaObserver().observe(this, Observer <EntradaBlogResponse?>{

            if(it  == null) {
                Toast.makeText(context, "Error al crear la entrada", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Entrada Publicada", Toast.LENGTH_LONG).show()
                super.dismiss()
            }
        })
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Companion.SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results!![0]
                }
            txtVDescripcion.text = ""+txtVDescripcion.text + " " +spokenText
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val SPEECH_REQUEST_CODE = 0
    }
}