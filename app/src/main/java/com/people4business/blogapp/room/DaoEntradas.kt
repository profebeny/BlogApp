package com.people4business.blogapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DaoEntradas {

    @Query("SELECT * FROM TB_Entradas")
     fun obtenerEntradas(): MutableList<TB_Entradas>

    @Insert
     fun agregarEntrada(tbEntrada: TB_Entradas)

    //se agregarán los elementos que no existen, los elementos que ya existen serán reemplazados por los nuevos.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEntrada(tbEntrada: TB_Entradas)

    @Query("DELETE FROM TB_Entradas")
    fun deleteEntradas()

}