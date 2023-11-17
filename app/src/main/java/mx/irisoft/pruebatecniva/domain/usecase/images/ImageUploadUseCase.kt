package mx.irisoft.pruebatecniva.domain.usecase.images

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.irisoft.pruebatecniva.data.firebase.repository.StorageRepository
import mx.irisoft.pruebatecniva.data.remote.state.Resource
import mx.irisoft.pruebatecniva.domain.models.ProgressUploadModel
import javax.inject.Inject

class ImageUploadUseCase @Inject constructor(
    private val storageRepository: StorageRepository,
) {
    suspend operator fun invoke(uri: Uri, response: (ProgressUploadModel) -> Unit) = withContext(Dispatchers.IO) {
        storageRepository.uploadImage(uri) { resource, total, progress ->
            response(ProgressUploadModel(resource, total, progress))
        }
    }
}