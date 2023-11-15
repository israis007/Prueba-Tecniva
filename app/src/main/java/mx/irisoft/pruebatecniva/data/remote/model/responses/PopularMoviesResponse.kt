package mx.irisoft.pruebatecniva.data.remote.model.responses

import com.google.gson.annotations.SerializedName
import mx.irisoft.pruebatecniva.data.remote.datasource.Results


data class PopularMoviesResponse(
    @SerializedName("page")                 val page                : Int,
    @SerializedName("results")              val results             : List<Results>,
    @SerializedName("total_pages")          val totalPages          : Int,
    @SerializedName("total_results")        val totalResults        : Int
)
