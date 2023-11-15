package mx.irisoft.pruebatecniva.domain.usecase.movies.local

import mx.irisoft.pruebatecniva.domain.enums.SearchType
import mx.irisoft.pruebatecniva.domain.repository.MoviesRepository
import javax.inject.Inject

class SearchByUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(filter: String, searchType: SearchType) =
        when(searchType) {
            SearchType.TITLE -> moviesRepository.searchByTitle(filter)
            SearchType.AUTHOR -> moviesRepository.searchByAuthor(filter)
            SearchType.CONTENT -> moviesRepository.searchByContent(filter)
        }

}