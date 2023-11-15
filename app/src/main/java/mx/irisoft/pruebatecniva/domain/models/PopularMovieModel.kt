package mx.irisoft.pruebatecniva.domain.models

data class PopularMovieModel(
    val page: Int,
    val results: List<PopularMovieItemModel>,
    val totalPages: Int
)
data class PopularMovieItemModel(
    val title: String,
    val originalTitle: String,
    val description: String,
    val releaseDate: String,
    val imagePath: String,
)
