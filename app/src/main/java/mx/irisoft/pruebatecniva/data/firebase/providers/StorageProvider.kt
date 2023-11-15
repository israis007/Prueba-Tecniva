package mx.irisoft.pruebatecniva.data.firebase.providers

import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.irisoft.pruebatecniva.utils.constants.DIRECTORY_IMAGES
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageProvider @Inject constructor() {

    @Provides
    @Singleton
    fun getStorageImages() =
        FirebaseStorage.getInstance().reference.child(DIRECTORY_IMAGES)
}