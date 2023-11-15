package com.people4business.blogapp.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EntradaInstance {

    companion object {

        val BASE_URL = "https://blogapp-78cc1-default-rtdb.firebaseio.com/"

        fun getRetroInstance(): Retrofit {
            val client = OkHttpClient.Builder()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}