package mx.irisoft.pruebatecniva.domain.mappers

import mx.irisoft.pruebatecniva.data.remote.datasource.KnowFor
import mx.irisoft.pruebatecniva.data.remote.datasource.ResultsPersons
import mx.irisoft.pruebatecniva.data.remote.model.responses.PopularPersonsResponse
import mx.irisoft.pruebatecniva.domain.models.KnowForModel
import mx.irisoft.pruebatecniva.domain.models.PopularPersonModel
import mx.irisoft.pruebatecniva.domain.models.PopularPersonsModel

object PopularPersonMapper {

    fun PopularPersonsResponse.map() =
        PopularPersonsModel(
            page = this.page,
            totalPages = this.totalPages,
            results = this.results.map { result -> result.map() }
        )

    private fun ResultsPersons.map() =
        PopularPersonModel(
            gender = this.gender,
            knownFor = this.knownFor.map { item -> item.map() },
            knownForDepartment = this.knownForDepartment,
            name = this.name,
            popularity = this.popularity,
            profilePath = this.profilePath
        )
    private fun KnowFor.map() =
        KnowForModel(
            isAdult = this.isAdult,
            backdropPath = this.backdropPath,
            originalLanguage = this.originalLanguage,
            originalTitle = this.originalTitle,
            overview = this.overview,
            posterPath = this.posterPath,
            releaseDate = this.releaseDate,
            title = this.title,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount
        )
}