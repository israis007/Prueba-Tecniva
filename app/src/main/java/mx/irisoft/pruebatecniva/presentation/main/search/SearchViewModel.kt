package mx.irisoft.pruebatecniva.presentation.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {

    fun searchmovie(movie: String){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}