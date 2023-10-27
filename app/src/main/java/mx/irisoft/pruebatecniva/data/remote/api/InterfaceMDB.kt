package mx.irisoft.pruebatecniva.data.remote.api

import mx.irisoft.pruebatecniva.data.remote.model.responses.PopularMoviesResponse
import mx.irisoft.pruebatecniva.data.remote.model.responses.PopularPersonsResponse
import mx.irisoft.pruebatecniva.utils.constants.API_KEY
import mx.irisoft.pruebatecniva.utils.constants.ENDPOINT
import mx.irisoft.pruebatecniva.utils.constants.LANGUAGE
import mx.irisoft.pruebatecniva.utils.constants.PAGE
import mx.irisoft.pruebatecniva.utils.constants.KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface InterfaceMDB {

    @GET("$ENDPOINT/movie/popular")
    suspend fun getPopularMovies(@Query(API_KEY) key: String = KEY, @Query(LANGUAGE) language: String, @Query(PAGE) page: Int) : Response<PopularMoviesResponse>

    @GET("$ENDPOINT/person/popular")
    suspend fun getPopularPersons(@Query(API_KEY) key: String = KEY, @Query(LANGUAGE) language: String, @Query(PAGE) page: Int) : Response<PopularPersonsResponse>


}