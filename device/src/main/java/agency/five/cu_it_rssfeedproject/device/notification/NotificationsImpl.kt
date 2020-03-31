package agency.five.cu_it_rssfeedproject.device.notification

import android.app.Notification
import androidx.core.app.NotificationManagerCompat

class NotificationsImpl(private val notificationManagerCompat: NotificationManagerCompat) :
    Notifications {

    override fun show(notificationId: Int, notification: Notification) =
        notificationManagerCompat.notify(notificationId, notification)

    override fun update(notificationId: Int, notification: Notification) =
        notificationManagerCompat.notify(notificationId, notification)

    override fun remove(notificationId: Int) = notificationManagerCompat.cancel(notificationId)

    override fun removeAll() = notificationManagerCompat.cancelAll()
}