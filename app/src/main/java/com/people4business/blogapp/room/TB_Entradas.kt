package com.people4business.blogapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TB_Entradas")
data class TB_Entradas(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "titulo") val titulo: String?= null,
    @ColumnInfo(name = "autor")val autor: String?= null,
    @ColumnInfo(name = "contenido")val contenido: String?= null,
    @ColumnInfo(name = "fechaHora")val fechaHora: String?= null)

