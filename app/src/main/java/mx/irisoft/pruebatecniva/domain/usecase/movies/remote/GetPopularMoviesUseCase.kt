package mx.irisoft.pruebatecniva.domain.usecase.movies.remote

import mx.irisoft.pruebatecniva.data.remote.state.Resource
import mx.irisoft.pruebatecniva.data.remote.state.StatusType
import mx.irisoft.pruebatecniva.data.repository.MDBRepository
import mx.irisoft.pruebatecniva.domain.mappers.PopularMovieMapper.map
import mx.irisoft.pruebatecniva.domain.models.PopularMovieModel
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val mdbRepository: MDBRepository
) {

    suspend operator fun invoke(
        page: Int,
        response: (listMovies: Resource<PopularMovieModel?>) -> Unit
    ) =
        mdbRepository.getMovies(page = page) { resource ->
            response(
                when (resource.statusType) {
                    StatusType.SUCCESS -> Resource.success(resource.data?.map())
                    StatusType.ERROR -> Resource.error(resource.message)
                    StatusType.LOADING -> Resource.loading()
                }
            )
        }
}