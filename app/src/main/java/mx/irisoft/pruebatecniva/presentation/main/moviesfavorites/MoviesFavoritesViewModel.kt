package mx.irisoft.pruebatecniva.presentation.main.moviesfavorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesFavoritesViewModel @Inject constructor(): ViewModel() {

    fun searchmovie(movie: String){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}