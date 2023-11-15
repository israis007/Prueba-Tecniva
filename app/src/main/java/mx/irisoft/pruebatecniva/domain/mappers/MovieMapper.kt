package mx.irisoft.pruebatecniva.domain.mappers

import mx.irisoft.pruebatecniva.data.local.room.entities.MovieLocal
import mx.irisoft.pruebatecniva.domain.models.MovieModel
import java.util.Calendar

object MovieMapper {

    fun MovieModel.map() =
        MovieLocal(
            id = this.id,
            title = this.title,
            author = this.author,
            date =  this.date.timeInMillis,
            content = this.content
        )

    fun MovieLocal.map() =
        MovieModel(
            id = this.id,
            title = this.title,
            author = this.author,
            date = Calendar.getInstance().apply {
                timeInMillis = this@map.date
            },
            content = this.content
        )
}