package mx.irisoft.pruebatecniva.data.firebase.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.irisoft.pruebatecniva.data.firebase.entities.NoteEntity
import mx.irisoft.pruebatecniva.data.firebase.providers.FirestoreProvider
import mx.irisoft.pruebatecniva.data.remote.state.Resource
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    private val firestoreProvider: FirestoreProvider,
) {

    suspend fun addNote(noteEntity: NoteEntity, resource: (Resource<String>) -> Unit) = withContext(Dispatchers.IO) {
        resource(Resource.loading())
        firestoreProvider.getStorageNotes().add(noteEntity).addOnSuccessListener {
            resource(Resource.success(it.id))
        }.addOnFailureListener {
            resource(Resource.error(it.message ?: "Not Found"))
        }
    }
}