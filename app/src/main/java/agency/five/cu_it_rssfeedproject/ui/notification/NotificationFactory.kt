package agency.five.cu_it_rssfeedproject.ui.notification

import android.app.Notification
import android.app.PendingIntent

interface NotificationFactory {

    fun createNewFeedItemsNotification(contentIntent: PendingIntent): Notification
}