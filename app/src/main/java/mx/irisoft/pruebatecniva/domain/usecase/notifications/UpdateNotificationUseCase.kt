package mx.irisoft.pruebatecniva.domain.usecase.notifications

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.irisoft.pruebatecniva.domain.notifications.NotificationCenter
import javax.inject.Inject

class UpdateNotificationUseCase @Inject constructor(
    private val notificationCenter: NotificationCenter,
) {

    suspend operator fun invoke(total: Long, transfered: Long) = withContext(Dispatchers.IO) {
        notificationCenter.updateProgress(total, transfered)
    }
}