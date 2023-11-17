package mx.irisoft.pruebatecniva.domain.usecase.images

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.irisoft.pruebatecniva.data.firebase.repository.StorageRepository
import mx.irisoft.pruebatecniva.data.remote.state.Resource
import javax.inject.Inject

class GetListImagesUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    suspend operator fun invoke(response: (Resource<List<String>>) -> Unit) = withContext(Dispatchers.IO) {
        storageRepository.getListImages {
            response(it)
        }
    }
}