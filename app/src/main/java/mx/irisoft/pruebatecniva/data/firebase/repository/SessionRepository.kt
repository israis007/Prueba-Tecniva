package mx.irisoft.pruebatecniva.data.firebase.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.irisoft.pruebatecniva.BuildConfig
import mx.irisoft.pruebatecniva.data.firebase.providers.SessionProvider
import mx.irisoft.pruebatecniva.data.remote.state.Resource
import javax.inject.Inject

class SessionRepository @Inject constructor(
    private val authProvider: SessionProvider,
) {

    suspend fun login(isLogin: (login: Resource<Boolean>) -> Unit) = withContext(Dispatchers.IO) {
        isLogin(Resource.loading())
        authProvider.getAuth().signInWithEmailAndPassword(BuildConfig.USER_FB, BuildConfig.PASS_FB)
            .addOnCompleteListener { task ->
                isLogin(Resource.success(task.isSuccessful))
            }.addOnFailureListener { fail ->
                isLogin(Resource.error(fail.message ?: "Error al iniciar sesión"))
            }
    }
}