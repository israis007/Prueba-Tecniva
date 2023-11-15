package mx.irisoft.pruebatecniva.data.repository

import mx.irisoft.pruebatecniva.data.remote.model.responses.PopularMoviesResponse
import mx.irisoft.pruebatecniva.data.remote.model.responses.PopularPersonsResponse
import mx.irisoft.pruebatecniva.data.remote.providers.MDBApiProvider
import mx.irisoft.pruebatecniva.data.remote.state.Resource
import mx.irisoft.pruebatecniva.utils.constants.LANGUAGE_MDB
import mx.irisoft.pruebatecniva.utils.constants.RETROFIT_MDB
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class MDBRepository @Inject constructor(
    private val providesAPI: MDBApiProvider,
    @Named(RETROFIT_MDB) private val retrofit: Retrofit
) {

    suspend fun getMovies(
        page: Int,
        response: (popularMoviesResponse: Resource<PopularMoviesResponse>) -> Unit
    ) {
        response(Resource.loading())
        try {
            val temp = providesAPI.mbdAPI(retrofit).getPopularMovies(language = LANGUAGE_MDB, page = page)
            if (temp.isSuccessful && temp.code() == 200)
                response(Resource.success(temp.body()!!))
            else
                response(Resource.error("No se pudo comunicar con el servidor de MBD"))
        } catch (e: Exception) {
            response(Resource.error("Error interno: ${e.message}"))
        }
    }

    suspend fun getPopularPerson(
        page: Int,
        response: (popularPersonsResponse: Resource<PopularPersonsResponse>) -> Unit
    ){
        response(Resource.loading())
        try {
            val temp = providesAPI.mbdAPI(retrofit).getPopularPersons(language = LANGUAGE_MDB, page = page)
            if (temp.isSuccessful && temp.code() == 200)
                response(Resource.success(temp.body()!!))
            else
                response(Resource.error("No se pudo comunicar con el servidor de MBD"))
        } catch (e: Exception){
            response(Resource.error("Error interno: ${e.message}"))
        }
    }

}