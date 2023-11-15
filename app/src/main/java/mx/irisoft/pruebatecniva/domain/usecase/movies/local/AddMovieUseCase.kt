package mx.irisoft.pruebatecniva.domain.usecase.movies.local

import mx.irisoft.pruebatecniva.domain.mappers.PopularMovieMapper.map
import mx.irisoft.pruebatecniva.domain.models.MovieModel
import mx.irisoft.pruebatecniva.domain.models.PopularMovieItemModel
import mx.irisoft.pruebatecniva.domain.models.PopularMovieModel
import mx.irisoft.pruebatecniva.domain.repository.MoviesRepository
import javax.inject.Inject

class AddMovieUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieModel: PopularMovieItemModel) =
        moviesRepository.add(movieModel.map())

}