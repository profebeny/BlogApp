package com.people4business.blogapp
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.people4business.blogapp.adapters.MyAdapter
import com.people4business.blogapp.data.EntradaBlog
import com.people4business.blogapp.databinding.ActivityMainBinding
import com.people4business.blogapp.fragments.Entrada
import com.people4business.blogapp.fragments.NuevaEntrada
import com.people4business.blogapp.room.DBEntradas
import com.people4business.blogapp.room.TB_Entradas
import com.people4business.blogapp.utilidades.Funciones
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var TAG: String = "PRUEBAS_LOG"
    private lateinit var binding: ActivityMainBinding
    val funciones = Funciones()

    private lateinit var dbref: DatabaseReference
    private lateinit var Recyclerview: RecyclerView
    private lateinit var EntradasArrayList: ArrayList<EntradaBlog>
    private lateinit var EntradasFiltradas: ArrayList<EntradaBlog>
    private lateinit var adapter: MyAdapter

    lateinit var room: DBEntradas
    lateinit var tb_Entradas: TB_Entradas
    var listaTB_Entradas: MutableList<TB_Entradas> = mutableListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Recyclerview = findViewById(R.id.userList)
        Recyclerview.layoutManager = LinearLayoutManager(this)
        Recyclerview.setHasFixedSize(true)
        EntradasArrayList = arrayListOf<EntradaBlog>()
        EntradasFiltradas = EntradasArrayList

        room = Room.databaseBuilder(this, DBEntradas::class.java, "dbEntradas").build()
        //eliminarEntradas(room);
        //====== Si la Red no está disponible trabajará con el modo OffLine
        if (!funciones.isNetDisponible(this)) {
            obtenerEntradas(room)
        }
        getEntradasOnline()
        //======


        status()

        binding.txtBuscar.addTextChangedListener { tituloFilter ->
            EntradasFiltradas = EntradasArrayList.filter { EntradaBlog ->
                EntradaBlog.titulo!!.lowercase().contains(
                    tituloFilter.toString().lowercase()
                ) || EntradaBlog.autor!!.lowercase().contains(
                    tituloFilter.toString().lowercase()
                ) || EntradaBlog.contenido!!.lowercase()
                    .contains(tituloFilter.toString().lowercase())
            } as ArrayList<EntradaBlog>
            adapter.UpdateEntradas(EntradasFiltradas as ArrayList<EntradaBlog>)
        }

        binding.fabNuevaEntrada.setOnClickListener {
            if (funciones.isNetDisponible(this)) {
                val nuevaEntrada = NuevaEntrada()
                nuevaEntrada.show(supportFragmentManager, "Nueva Entrada")
            }
        }
    }

    private fun status() {
        val myHandler = Handler(Looper.getMainLooper())

        myHandler.post(object : Runnable {
            override fun run() {
                if(funciones.isNetDisponible_MSG(this@MainActivity))
                {
                    binding.txtStatus.text = "online"
                    binding.imgIndicador.setImageDrawable(getResources().getDrawable(R.drawable.img_online))
                }else{
                    binding.txtStatus.text = "offline"
                    binding.imgIndicador.setImageDrawable(getResources().getDrawable(R.drawable.img_offline))
                }
                myHandler.postDelayed(this, 1000 )
            }
        })

    }

    private fun getEntradasOnline() {
        dbref = FirebaseDatabase.getInstance().getReference("entradas")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i(TAG,"onDataChange")
                if(!EntradasArrayList.isEmpty())EntradasArrayList.clear()


                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        Log.i(TAG,"userSnapshot:"+userSnapshot.key)

                        val entradaBlog = userSnapshot.getValue(EntradaBlog::class.java)
                        EntradasArrayList.add(entradaBlog!!)
                        tb_Entradas = TB_Entradas(userSnapshot.key.toString(),entradaBlog.titulo,entradaBlog.autor,entradaBlog.contenido,entradaBlog.fechaHora)

                        //Agregar a la base Room
                        agregarEntrada(room, tb_Entradas)
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

    fun agregarEntrada(room: DBEntradas, tb_Entradas: TB_Entradas) {
        lifecycleScope.launch(Dispatchers.IO) {
            room.daoEntradas().insertEntrada(tb_Entradas)
        }
    }

    fun eliminarEntradas(room: DBEntradas) {
        lifecycleScope.launch(Dispatchers.IO) {
            room.daoEntradas().deleteEntradas()
        }
    }
    fun obtenerEntradas(room: DBEntradas) {
        lifecycleScope.launch(Dispatchers.IO) {
            //Limpia EntradasArrayList que es la lista original para no duplicar valores al solicitarlos a la base.
            if(!listaTB_Entradas.isEmpty())EntradasArrayList.clear()

            listaTB_Entradas = room.daoEntradas().obtenerEntradas()
            Log.i(TAG, listaTB_Entradas.toString() + "\n")

                runOnUiThread {
                    listaTB_Entradas.forEach {
                        EntradasArrayList.add(EntradaBlog(it.titulo,it.autor,it.contenido,it.fechaHora)
                        )
                    }
                    llenarRv()
                }
        }
    }

    private fun llenarRv() {
        adapter = MyAdapter(EntradasArrayList)
        Recyclerview.adapter = adapter
        adapter.onItemClickListener(object : MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@MainActivity,"Entrada"+position, Toast.LENGTH_SHORT).show()
                val entrada = Entrada(EntradasFiltradas[position].titulo,EntradasFiltradas[position].autor,EntradasFiltradas[position].contenido,EntradasFiltradas[position].fechaHora)
                entrada.show(supportFragmentManager,"Entrada")
            }
        })
    }
}