package mx.irisoft.pruebatecniva.presentation.main.upload

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.irisoft.pruebatecniva.data.remote.state.Resource
import mx.irisoft.pruebatecniva.domain.models.ProgressUploadModel
import mx.irisoft.pruebatecniva.domain.usecase.images.GetListImagesUseCase
import mx.irisoft.pruebatecniva.domain.usecase.images.GetURLImageUseCase
import mx.irisoft.pruebatecniva.domain.usecase.images.ImageUploadUseCase
import mx.irisoft.pruebatecniva.domain.usecase.notifications.CompleteNotificationUseCase
import mx.irisoft.pruebatecniva.domain.usecase.notifications.CreateNotificationUseCase
import mx.irisoft.pruebatecniva.domain.usecase.notifications.UpdateNotificationUseCase
import javax.inject.Inject

@HiltViewModel
class UploadImagesViewModel @Inject constructor(
    private val imageUploadUseCase: ImageUploadUseCase,
    private val getURLImageUseCase: GetURLImageUseCase,
    private val getListImagesUseCase: GetListImagesUseCase,
    private val createNotificationUseCase: CreateNotificationUseCase,
    private val updateNotificationUseCase: UpdateNotificationUseCase,
    private val completeNotificationUseCase: CompleteNotificationUseCase
) : ViewModel() {

    private val _uploadSuccess = MutableLiveData<ProgressUploadModel>()
    val uploadSuccess: LiveData<ProgressUploadModel> get() = _uploadSuccess

    private val _listImages = MutableLiveData<Resource<List<String>>>()
    val listImages: LiveData<Resource<List<String>>> get() = _listImages

    private val _urlImage = MutableLiveData<Resource<String>>()
    val urlImage: LiveData<Resource<String>> get() = _urlImage

    fun uploadImage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            imageUploadUseCase.invoke(uri) {
                _uploadSuccess.postValue(it)
            }
        }
    }

    fun getListImages() {
        viewModelScope.launch(Dispatchers.IO) {
            getListImagesUseCase.invoke {
                _listImages.postValue(it)
            }
        }
    }

    fun getURLImage(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getURLImageUseCase.invoke(name) {
                _urlImage.postValue(it)
            }
        }
    }

    fun createNotification() {
        viewModelScope.launch(Dispatchers.IO) {
            createNotificationUseCase.invoke()
        }
    }

    fun updateNotification(total: Long, transfered: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            updateNotificationUseCase.invoke(total, transfered)
        }
    }

    fun completeNotification() {
        viewModelScope.launch(Dispatchers.IO) {
            completeNotificationUseCase.invoke()
        }
    }
}