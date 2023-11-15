package mx.irisoft.pruebatecniva.presentation.main.moviesfavorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.irisoft.pruebatecniva.domain.models.MovieModel
import mx.irisoft.pruebatecniva.domain.usecase.movies.local.DeleteMovieUseCase
import mx.irisoft.pruebatecniva.domain.usecase.movies.local.GetAllMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesFavoritesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
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
    fun searchmovie(movie: String) {
        viewModelScope.launch(Dispatchers.IO) {
        }
    }
}