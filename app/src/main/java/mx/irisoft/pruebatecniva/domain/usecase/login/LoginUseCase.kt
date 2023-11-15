package mx.irisoft.pruebatecniva.domain.usecase.login

import mx.irisoft.pruebatecniva.data.remote.state.Resource
import mx.irisoft.pruebatecniva.data.firebase.repository.SessionRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {

    suspend operator fun invoke(isLogin: (login: Resource<Boolean>) -> Unit) =
        sessionRepository.login {
            isLogin(it)
        }

}