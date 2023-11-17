package mx.irisoft.pruebatecniva.domain.usecase.images

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.irisoft.pruebatecniva.data.firebase.repository.StorageRepository
import mx.irisoft.pruebatecniva.data.remote.state.Resource
import javax.inject.Inject

class GetURLImageUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    suspend operator fun invoke(file: String, response: (Resource<String>) -> Unit) = withContext(Dispatchers.IO) {
        storageRepository.getUrlDownloadImage(file) {
            response(it)
        }
    }
}