package mx.irisoft.pruebatecniva.data.firebase.repository

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.irisoft.pruebatecniva.data.firebase.providers.StorageProvider
import mx.irisoft.pruebatecniva.data.remote.state.Resource
import mx.irisoft.pruebatecniva.utils.extensions.Extensions.convertToByteArray
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val storage: StorageProvider,
) {

    suspend fun uploadImage(uri: Uri?, response: (name: Resource<String>, total: Long, progress: Long) -> Unit) = withContext(Dispatchers.IO) {
        response(Resource.loading(), 0, 0)
        try {
            val bytes = uri?.convertToByteArray()
            val name = uri?.toString()?.substringAfterLast('/')
            if (bytes != null && !name.isNullOrEmpty()) {
                storage.getStorageImages().child(name)
                    .putBytes(bytes)
                    .addOnCompleteListener { complete ->
                        if (complete.isSuccessful) {
                            response(Resource.success(name), 0, 0)
                        } else {
                            response(Resource.error("Error al subir la imagen, intente más tarde"), 0, 0)
                        }
                    }.addOnFailureListener { fail ->
                        response(Resource.error(fail.message ?: "Error al subir la imagen, intente más tarde"), 0, 0)
                    }.addOnProgressListener { upload ->
                        response(Resource.loading(), upload.totalByteCount, upload.bytesTransferred)
                    }
            }
        } catch (e: Exception) {
            response(Resource.error("No se pudo subir la imagen, intente más tarde..."), 0, 0)
        }
    }

    suspend fun getUrlDownloadImage(nameFile: String, response: (name: Resource<String>) -> Unit) = withContext(Dispatchers.IO) {
        response(Resource.loading())
        try {
            storage.getStorageImages().child(nameFile).downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    response(Resource.success(task.result.toString()))
                } else {
                    response(Resource.error("Error al obtener la url de la imagen"))
                }
            }.addOnFailureListener { fail ->
                response(Resource.error(fail.message ?: "Error al obtener la url de la imagen"))
            }
        } catch (e: Exception) {
            response(Resource.error("Error al obtener la url de la imagen"))
        }
    }

    suspend fun getListImages(response: (list: Resource<List<String>>) -> Unit) = withContext(Dispatchers.IO) {
        response(Resource.loading())
        try {
            storage.getStorageImages().listAll().addOnCompleteListener { task ->
                val array = ArrayList<String>()
                if (task.isSuccessful) {
                    task.result.items.forEach {
                        array.add(it.name)
                    }
                    response(Resource.success(array.toList()))
                } else {
                    response(Resource.error("Error al obtener la lista de imágenes"))
                }
            }.addOnFailureListener { fail ->
                response(Resource.error(fail.message ?: "Error al obtener la lista de imágenes"))
            }
        } catch (e: Exception) {
            response(Resource.error("Error al obtener la lista de imágenes"))
        }
    }
}