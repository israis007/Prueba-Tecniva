package mx.irisoft.pruebatecniva.data.local.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import mx.irisoft.pruebatecniva.data.local.room.entities.MovieLocal

@Dao
interface MovieLocalDao {

    @Insert
    suspend fun insertMovieLocal(movieLocal: MovieLocal)

    @Delete
    suspend fun deleteMovieLocal(movieLocal: MovieLocal)

    @Update
    suspend fun updateMovieLocal(movieLocal: MovieLocal)

    @Query( "SELECT * FROM MovieLocal")
    suspend fun getAllMovies(): List<MovieLocal>

    @Query( "SELECT * FROM MovieLocal WHERE title LIKE :title")
    suspend fun getMoviesByTitle(title: String): List<MovieLocal>

    @Query( "SELECT * FROM MovieLocal WHERE content LIKE :content")
    suspend fun getMoviesByContent(content: String): List<MovieLocal>

    @Query( "SELECT * FROM MovieLocal WHERE author LIKE :author")
    suspend fun getMoviesByAuthor(author: String): List<MovieLocal>
}