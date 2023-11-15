package mx.irisoft.pruebatecniva.presentation.main.persons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.irisoft.pruebatecniva.data.remote.state.Resource
import mx.irisoft.pruebatecniva.domain.models.PopularPersonsModel
import mx.irisoft.pruebatecniva.domain.usecase.movies.remote.GetPopularPersonsUseCase
import javax.inject.Inject

@HiltViewModel
class PersonsViewModel @Inject constructor(
    private val getPopularPersonsUseCase: GetPopularPersonsUseCase
): ViewModel(){

    private val _listPersons = MutableLiveData<Resource<PopularPersonsModel?>>()
    val listPersons: LiveData<Resource<PopularPersonsModel?>> get() = _listPersons

    fun getPopularPersons(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getPopularPersonsUseCase(page) {
                _listPersons.postValue(it)
            }
        }
    }

}