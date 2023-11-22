package mx.irisoft.pruebatecniva.data.firebase.entities

import com.google.firebase.Timestamp

data class NoteEntity(
    val title: String,
    val author: String,
    val publishedDated: Timestamp,
    val content: String,
)