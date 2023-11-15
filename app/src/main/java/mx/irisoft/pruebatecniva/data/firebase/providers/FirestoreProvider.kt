package mx.irisoft.pruebatecniva.data.firebase.providers

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.irisoft.pruebatecniva.utils.constants.COLLECTION_STORAGE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirestoreProvider {

    @Provides
    @Singleton
    fun getStorageImages() =
        FirebaseFirestore.getInstance().collection(COLLECTION_STORAGE)
}