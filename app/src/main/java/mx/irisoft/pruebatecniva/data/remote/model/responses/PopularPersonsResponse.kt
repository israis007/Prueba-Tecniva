package mx.irisoft.pruebatecniva.data.remote.model.responses

import com.google.gson.annotations.SerializedName
import mx.irisoft.pruebatecniva.data.remote.datasource.ResultsPersons


data class PopularPersonsResponse(
    @SerializedName("page") val page : Int,
    @SerializedName("results") val results : List<ResultsPersons>,
    @SerializedName("total_pages") val total_pages : Int,
    @SerializedName("total_results") val total_results : Int
)
