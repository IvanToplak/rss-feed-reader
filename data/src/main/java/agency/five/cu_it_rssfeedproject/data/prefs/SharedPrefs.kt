package agency.five.cu_it_rssfeedproject.data.prefs

interface SharedPrefs {

    fun getNewFeedItemsNotificationPref(): Boolean

    fun setNewFeedItemsNotificationPref(newFeedItemsNotificationEnabled: Boolean)
}