package mx.irisoft.pruebatecniva.domain.models

data class PopularPersonsModel (
    val page                : Int,
    val results             : List<PopularPersonModel>,
    val totalPages          : Int
)

data class PopularPersonModel (
    val gender              : Int,
    val knownFor            : List<KnowForModel>,
    val knownForDepartment  : String,
    val name                : String,
    val popularity          : Double,
    val profilePath         : String
)
data class KnowForModel (
    val isAdult                 : Boolean,
    val backdropPath            : String,
    val originalLanguage        : String,
    val originalTitle           : String?,
    val overview                : String,
    val posterPath              : String,
    val releaseDate             : String,
    val title                   : String?,
    val voteAverage             : Float,
    val voteCount               : Int,
)