package com.people4business.blogapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.people4business.blogapp.databinding.ActivityMainBinding
import com.people4business.blogapp.fragments.NuevaEntrada
import com.people4business.blogapp.adapters.MyAdapter
import com.people4business.blogapp.data.EntradaBlog
import com.people4business.blogapp.fragments.Entrada
import com.people4business.blogapp.utilidades.Funciones

class MainActivity : AppCompatActivity() {
    private var TAG:String = "PRUEBAS_LOG"
    private lateinit var binding:ActivityMainBinding
    val funciones = Funciones()

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var EntradasArrayList : ArrayList<EntradaBlog>
    private lateinit var EntradasFiltradas : ArrayList<EntradaBlog>
    private lateinit var adapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        EntradasArrayList = arrayListOf<EntradaBlog>()


        getEntradasOnline()

        binding.txtBuscar.addTextChangedListener {tituloFilter ->
            EntradasFiltradas =
                EntradasArrayList.filter { EntradaBlog -> EntradaBlog.titulo!!.lowercase().contains(tituloFilter.toString().lowercase()) || EntradaBlog.autor!!.lowercase().contains(tituloFilter.toString().lowercase()) || EntradaBlog.contenido!!.lowercase().contains(tituloFilter.toString().lowercase())} as ArrayList<EntradaBlog>
            adapter.UpdateEntradas(EntradasFiltradas as ArrayList<EntradaBlog>)
        }

        binding.fabNuevaEntrada.setOnClickListener{
            if (funciones.isNetDisponible(this))
            {
                val nuevaEntrada =NuevaEntrada()
                nuevaEntrada.show(supportFragmentManager,"Nueva Entrada")
            }
        }
    }

    private fun getEntradasOnline() {
        dbref = FirebaseDatabase.getInstance().getReference("entradas")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i(TAG,"onDataChange")

                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val entradaBlog = userSnapshot.getValue(EntradaBlog::class.java)
                        EntradasArrayList.add(entradaBlog!!)
                        Log.i(TAG,"entradaBlog")
                    }
                    llenarRv()
                }
                else
                {
                    Toast.makeText(this@MainActivity,"No existen entradas", Toast.LENGTH_SHORT).show()
                    Log.i(TAG,"not snapshot.exists()")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Toast.makeText(this@MainActivity,"ha ocurrido un error", Toast.LENGTH_SHORT).show()
                Log.i(TAG,"onCancelled:"+error)
            }
        })
    }

    private fun llenarRv() {
        adapter = MyAdapter(EntradasArrayList)
        userRecyclerview.adapter = adapter
        adapter.onItemClickListener(object : MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@MainActivity,"Entrada"+position, Toast.LENGTH_SHORT).show()
                val entrada = Entrada(EntradasFiltradas[position].titulo,EntradasFiltradas[position].autor,EntradasFiltradas[position].contenido,EntradasFiltradas[position].fechaHora)
                entrada.show(supportFragmentManager,"Entrada")
            }
        })
    }
}