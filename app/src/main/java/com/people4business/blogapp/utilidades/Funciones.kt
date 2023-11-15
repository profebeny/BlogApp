package com.people4business.blogapp.utilidades

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import java.time.LocalDateTime

class Funciones {

    fun isNetDisponible(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val actNetInfo = connectivityManager.activeNetworkInfo
        if(actNetInfo != null && actNetInfo.isConnected)
        {
            return true
        }else{
            Toast.makeText(context, "REVISA TU CONEXIÓN", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    fun isNetDisponible_MSG(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val actNetInfo = connectivityManager.activeNetworkInfo
        return actNetInfo != null && actNetInfo.isConnected
    }

    fun dataTime():String {
        val datetime = LocalDateTime.now()
        return datetime.toString()
        //println() // 2017-01-01T22:27:06.006276200
    }


}