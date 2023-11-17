package mx.irisoft.pruebatecniva.domain.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.irisoft.pruebatecniva.AppTest
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.utils.extensions.formatKyloBytes
import mx.irisoft.pruebatecniva.utils.extensions.formatKyloBytesInt
import javax.inject.Inject

private const val CHANNEL_ID = "5580310227"
private const val NOTIFICATION_ID = 9264

@Module
@InstallIn(SingletonComponent::class)
class NotificationCenter @Inject constructor() {

    private val notificationBuilder by lazy {
        NotificationCompat.Builder(AppTest.instance, CHANNEL_ID).apply {
            setContentTitle("Subida en Progreso")
            setContentText("Subiendo imagen...")
            setSmallIcon(R.drawable.ic_cloud_upload) // Reemplaza con tu icono
            priority = NotificationCompat.PRIORITY_HIGH
            setOngoing(true)
            setOnlyAlertOnce(true)
            setProgress(
                100,
                0,
                true,
            ) // 100 es el máximo, 0 es el progreso inicial, true para modo indeterminado
        }
    }

    private val notificationManager by lazy {
        NotificationManagerCompat.from(AppTest.instance)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = AppTest.instance.getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, channelName, importance)
            val notificationManager = AppTest.instance.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun createNotification() {
        if (checkPermission(AppTest.instance)) {
            createChannel()
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    fun updateProgress(totalByteCount: Long, bytesTransferred: Long) {
        notificationBuilder.setProgress(totalByteCount.formatKyloBytesInt(), bytesTransferred.formatKyloBytesInt(), false)
        notificationBuilder.setContentText("${bytesTransferred.formatKyloBytes()} subidos...")
        if (checkPermission(AppTest.instance)) {
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    fun completeProgress() {
        notificationBuilder.setContentText("Carga completa")
        notificationBuilder.setProgress(0, 0, false)
        notificationBuilder.setOngoing(false)
        if (checkPermission(AppTest.instance)) {
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun checkPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Check Android S permission to show notifications
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else if (Build.VERSION.SDK_INT in Build.VERSION_CODES.O..Build.VERSION_CODES.S_V2) { // Check if notification are blocked
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        } else { // Notifications default are enabled
            true
        }
    }
}