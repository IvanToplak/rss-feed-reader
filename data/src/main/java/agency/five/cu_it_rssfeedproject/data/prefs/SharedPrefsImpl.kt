package agency.five.cu_it_rssfeedproject.data.prefs

import android.content.Context

private const val NEW_FEED_ITEM_NOTIFICATION_PREF_KEY = "NEW_FEED_ITEM_NOTIFICATION_PREF"
private const val PREFERENCE_FILE_KEY = "agency.five.cu_it_rssfeedproject.USER_PREFS"

class SharedPrefsImpl(context: Context) : SharedPrefs {

    private val sharedPref =
        context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

    override fun getNewFeedItemsNotificationPref() =
        sharedPref.getBoolean(NEW_FEED_ITEM_NOTIFICATION_PREF_KEY, false)

    override fun setNewFeedItemsNotificationPref(newFeedItemsNotificationEnabled: Boolean) =
        sharedPref.edit()
            .putBoolean(NEW_FEED_ITEM_NOTIFICATION_PREF_KEY, newFeedItemsNotificationEnabled)
            .apply()

}