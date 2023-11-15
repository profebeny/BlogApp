package com.people4business.blogapp.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TB_Entradas::class],
    version = 1
)
abstract class DBEntradas: RoomDatabase() {
    abstract fun daoEntradas(): DaoEntradas
}