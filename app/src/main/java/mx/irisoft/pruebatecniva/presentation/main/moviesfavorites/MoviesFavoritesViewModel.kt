package mx.irisoft.pruebatecniva.presentation.main.moviesfavorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.irisoft.pruebatecniva.domain.enums.SearchType
import mx.irisoft.pruebatecniva.domain.models.MovieModel
import mx.irisoft.pruebatecniva.domain.usecase.movies.local.DeleteMovieUseCase
import mx.irisoft.pruebatecniva.domain.usecase.movies.local.GetAllMoviesUseCase
import mx.irisoft.pruebatecniva.domain.usecase.movies.local.SearchByUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesFavoritesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val searchByUseCase: SearchByUseCase

) : ViewModel() {

    private val _listMovies = MutableLiveData<List<MovieModel>>()
    val listMovies: LiveData<List<MovieModel>> get() = _listMovies

    fun getAllMoviesLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            _listMovies.postValue(getAllMoviesUseCase.invoke())
        }
    }

    fun deleteMovieLocal(movieModel: MovieModel) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMovieUseCase.invoke(movieModel)
        }
    }
    fun searchmovie(filter: String, searchType: SearchType) {
        viewModelScope.launch(Dispatchers.IO) {
            _listMovies.postValue(searchByUseCase.invoke(filter, searchType))
        }
    }
}