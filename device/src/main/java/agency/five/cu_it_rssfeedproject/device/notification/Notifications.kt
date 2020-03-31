package agency.five.cu_it_rssfeedproject.device.notification

import android.app.Notification

interface Notifications {

    fun show(notificationId: Int, notification: Notification)

    fun update(notificationId: Int, notification: Notification)

    fun remove(notificationId: Int)

    fun removeAll()
}