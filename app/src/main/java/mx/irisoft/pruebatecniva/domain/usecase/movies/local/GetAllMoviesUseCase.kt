package mx.irisoft.pruebatecniva.domain.usecase.movies.local

import mx.irisoft.pruebatecniva.domain.repository.MoviesRepository
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke() =
        moviesRepository.getAll()

}