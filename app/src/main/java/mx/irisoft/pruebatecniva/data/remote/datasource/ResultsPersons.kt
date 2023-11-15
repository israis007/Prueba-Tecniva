package mx.irisoft.pruebatecniva.data.remote.datasource

import com.google.gson.annotations.SerializedName

data class ResultsPersons(
    @SerializedName("adult")                    val isAdult                 : Boolean,
    @SerializedName("gender")                   val gender                  : Int,
    @SerializedName("id")                       val id                      : Int,
    @SerializedName("known_for")                val knownFor                : List<KnowFor>,
    @SerializedName("known_for_department")     val knownForDepartment      : String,
    @SerializedName("name")                     val name                    : String,
    @SerializedName("popularity")               val popularity              : Double,
    @SerializedName("profile_path")             val profilePath             : String
)
