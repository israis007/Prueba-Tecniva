package mx.irisoft.pruebatecniva.domain.usecase.notifications

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.irisoft.pruebatecniva.domain.notifications.NotificationCenter
import javax.inject.Inject

class CompleteNotificationUseCase @Inject constructor(
    private val notificationCenter: NotificationCenter,
) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        notificationCenter.completeProgress()
    }
}