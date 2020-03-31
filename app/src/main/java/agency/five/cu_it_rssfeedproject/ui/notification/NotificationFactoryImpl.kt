package agency.five.cu_it_rssfeedproject.ui.notification

import agency.five.cu_it_rssfeedproject.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

private const val NEW_FEED_ITEMS_NOTIFICATION_CHANNEL_NAME = "New feed items notification"
private const val NEW_FEED_ITEMS_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications when new feed items appear"
private const val NEW_FEED_ITEMS_NOTIFICATION_CHANNEL_ID =
    "agency.five.cu_it_rssfeedproject.NEW_FEED_ITEMS_NOTIFICATION_CHANNEL"
private const val NEW_FEED_ITEMS_NOTIFICATION_TITLE = "New articles!"
private const val NEW_FEED_ITEMS_NOTIFICATION_TEXT =
    "There have been some new articles posted in your feeds"

class NotificationFactoryImpl(
    private val context: Context,
    private val notificationManagerCompat: NotificationManagerCompat
) : NotificationFactory {

    override fun createNewFeedItemsNotification(contentIntent: PendingIntent): Notification {
        createNewFeedItemsNotificationChannel()
        return createNewFeedItemsNotificationInternal(contentIntent)
    }

    private fun createNewFeedItemsNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NEW_FEED_ITEMS_NOTIFICATION_CHANNEL_NAME
            val descriptionText = NEW_FEED_ITEMS_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                NEW_FEED_ITEMS_NOTIFICATION_CHANNEL_ID,
                name,
                importance
            ).apply {
                description = descriptionText
            }
            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

    private fun createNewFeedItemsNotificationInternal(contentIntent: PendingIntent) =
        NotificationCompat.Builder(context, NEW_FEED_ITEMS_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.new_feed_items_notification_small_icon_16dp)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setContentTitle(NEW_FEED_ITEMS_NOTIFICATION_TITLE)
            .setContentText(NEW_FEED_ITEMS_NOTIFICATION_TEXT)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .build()
}