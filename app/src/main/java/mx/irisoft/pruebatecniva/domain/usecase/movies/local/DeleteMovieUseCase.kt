package mx.irisoft.pruebatecniva.domain.usecase.movies.local

import mx.irisoft.pruebatecniva.domain.models.MovieModel
import mx.irisoft.pruebatecniva.domain.repository.MoviesRepository
import javax.inject.Inject

class DeleteMovieUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(movieModel: MovieModel) =
        moviesRepository.remove(movieModel)

}