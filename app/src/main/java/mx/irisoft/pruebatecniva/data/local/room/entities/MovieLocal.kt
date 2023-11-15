package mx.irisoft.pruebatecniva.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieLocal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val date: Long = System.currentTimeMillis(),
    val content: String
)
