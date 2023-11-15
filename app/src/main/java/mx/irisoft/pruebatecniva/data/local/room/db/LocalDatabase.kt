package mx.irisoft.pruebatecniva.data.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import mx.irisoft.pruebatecniva.data.local.room.daos.MovieLocalDao
import mx.irisoft.pruebatecniva.data.local.room.entities.MovieLocal

@Database(entities = [
    MovieLocal::class
], version = 1)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun movieLocal(): MovieLocalDao
}