package com.people4business.blogapp.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("entradas.json")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun createEntrada(@Body params: EntradaBlog): Call<EntradaBlogResponse>


}