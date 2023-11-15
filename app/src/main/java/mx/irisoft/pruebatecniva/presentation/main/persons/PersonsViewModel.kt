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
import mx.irisoft.pruebatecniva.domain.usecase.login.LoginUseCase
import mx.irisoft.pruebatecniva.domain.usecase.movies.remote.GetPopularPersonsUseCase
import javax.inject.Inject

@HiltViewModel
class PersonsViewModel @Inject constructor(
    private val getPopularPersonsUseCase: GetPopularPersonsUseCase,
    private val loginUseCase: LoginUseCase
): ViewModel(){

    private val _listPersons = MutableLiveData<Resource<PopularPersonsModel?>>()
    val listPersons: LiveData<Resource<PopularPersonsModel?>> get() = _listPersons

    private val _session = MutableLiveData<Resource<Boolean>>()
    val session: LiveData<Resource<Boolean>> get() = _session

    fun getPopularPersons(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getPopularPersonsUseCase(page) {
                _listPersons.postValue(it)
            }
        }
    }

    fun login(){
        viewModelScope.launch(Dispatchers.IO){
            loginUseCase.invoke {
                _session.postValue(it)
            }
        }
    }

}