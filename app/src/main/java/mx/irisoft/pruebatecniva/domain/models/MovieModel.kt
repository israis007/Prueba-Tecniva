package mx.irisoft.pruebatecniva.domain.models

import java.util.Calendar

data class MovieModel(
    val id: Int,
    val title: String,
    val author: String,
    val date: Calendar,
    val content: String
)
