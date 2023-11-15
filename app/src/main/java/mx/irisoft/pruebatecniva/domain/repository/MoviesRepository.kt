package mx.irisoft.pruebatecniva.domain.repository

import mx.irisoft.pruebatecniva.data.local.room.daos.MovieLocalDao
import mx.irisoft.pruebatecniva.domain.mappers.MovieMapper.map
import mx.irisoft.pruebatecniva.domain.models.MovieModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val movieLocalDao: MovieLocalDao
){

    suspend fun add(movieModel: MovieModel) =
        movieLocalDao.insertMovieLocal(movieModel.map())

    suspend fun remove(movieModel: MovieModel) =
        movieLocalDao.deleteMovieLocal(movieModel.map())

    suspend fun update(movieModel: MovieModel) =
        movieLocalDao.updateMovieLocal(movieModel.map())

    suspend fun getAll() =
        movieLocalDao.getAllMovies().map { item ->
            item.map()
        }

    suspend fun searchByTitle(title: String) =
        movieLocalDao.getMoviesByTitle("%$title%").map { item ->
            item.map()
        }

    suspend fun searchByAuthor(author: String) =
        movieLocalDao.getMoviesByAuthor("%$author%").map { item ->
            item.map()
        }

    suspend fun searchByContent(content: String) =
        movieLocalDao.getMoviesByContent("%content%").map { item ->
            item.map()
        }
}