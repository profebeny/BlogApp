package com.people4business.blogapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.people4business.blogapp.data.ApiService
import com.people4business.blogapp.data.EntradaBlog
import com.people4business.blogapp.data.EntradaBlogResponse
import com.people4business.blogapp.data.EntradaInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NuevaEntradaViewModel: ViewModel() {
    lateinit var crearNuevaEntradaLiveData: MutableLiveData<EntradaBlogResponse?>
    init {
        crearNuevaEntradaLiveData = MutableLiveData()
    }

    fun getCreateNewEntradaObserver(): MutableLiveData<EntradaBlogResponse?> {
        return crearNuevaEntradaLiveData
    }


    fun createNewEntrada(entradaBlog: EntradaBlog) {
        val retroService  = EntradaInstance.getRetroInstance().create(ApiService::class.java)
        val call = retroService.createEntrada(entradaBlog)
        call.enqueue(object: Callback<EntradaBlogResponse> {
            override fun onFailure(call: Call<EntradaBlogResponse>, t: Throwable) {
                crearNuevaEntradaLiveData.postValue(null)
            }

            override fun onResponse(call: Call<EntradaBlogResponse>, response: Response<EntradaBlogResponse>) {
                if(response.isSuccessful) {
                    crearNuevaEntradaLiveData.postValue(response.body())
                } else {
                    crearNuevaEntradaLiveData.postValue(null)
                }
            }
        })
    }

}