package mx.irisoft.pruebatecniva.utils.constants

import mx.irisoft.pruebatecniva.BuildConfig


//Args between activities
const val ARG_EXTRAS = "argsExtras"

//Const retrofit
//Local Vars
const val ENDPOINT = "${BuildConfig.BASE_URL}/3"
const val KEY = BuildConfig.MDB_KEY

//Query fields
const val API_KEY = "api_key"
const val LANGUAGE = "language"
const val PAGE = "page"

//Constants Hilt provider MDBApi
const val CLIENT_MDB            = "clientMDB"
const val RETROFIT_MDB          = "retrofitMDB"