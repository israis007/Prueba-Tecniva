package mx.irisoft.pruebatecniva.presentation.main.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.irisoft.pruebatecniva.data.remote.state.Resource
import mx.irisoft.pruebatecniva.domain.models.PopularMovieItemModel
import mx.irisoft.pruebatecniva.domain.models.PopularMovieModel
import mx.irisoft.pruebatecniva.domain.usecase.movies.local.AddMovieUseCase
import mx.irisoft.pruebatecniva.domain.usecase.movies.remote.GetPopularMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val addMovieUseCase: AddMovieUseCase
) : ViewModel() {

    private val _responseMovies = MutableLiveData<Resource<PopularMovieModel?>>()
    val responseMovies: LiveData<Resource<PopularMovieModel?>> get() = _responseMovies

    fun getPopularMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            popularMoviesUseCase.invoke(page) {
                _responseMovies.postValue(it)
            }
        }
    }

    fun addMovieToFavorites(movieModel: PopularMovieItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            addMovieUseCase.invoke(movieModel)
        }
    }

}