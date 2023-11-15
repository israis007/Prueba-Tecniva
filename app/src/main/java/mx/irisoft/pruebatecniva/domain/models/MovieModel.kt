package mx.irisoft.pruebatecniva.domain.models

import java.util.Calendar

data class MovieModel(
    val id: Int = 0,
    val title: String,
    val author: String,
    val date: Calendar = Calendar.getInstance(),
    val content: String,
    val imagePath: String
)
