package mx.irisoft.pruebatecniva.domain.mappers

import mx.irisoft.pruebatecniva.BuildConfig
import mx.irisoft.pruebatecniva.data.remote.datasource.Results
import mx.irisoft.pruebatecniva.data.remote.model.responses.PopularMoviesResponse
import mx.irisoft.pruebatecniva.domain.models.PopularMovieItemModel
import mx.irisoft.pruebatecniva.domain.models.PopularMovieModel

object PopularMovieMapper {

    fun PopularMoviesResponse.map() =
        PopularMovieModel(
            page = this.page,
            results = this.results.map { item ->
                item.map()
            },
            totalPages = this.totalPages
        )

    fun Results.map() =
        PopularMovieItemModel(
            title = this.title,
            originalTitle = this.original_title,
            description = this.overview,
            releaseDate = this.release_date,
            imagePath = "{${BuildConfig.URL_IMGS}${this.poster_path}}"
        )
}