package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.ui.model.FeedViewData
import io.reactivex.Flowable

interface FeedsContract {

    interface View

    interface ViewModel {
        fun getFeeds(): Flowable<List<FeedViewData>>
        fun deleteFeed(feedViewData: FeedViewData)
        fun toggleNewFeedItemsNotificationPref()
        fun getNewFeedItemsNotificationPref(): Boolean
    }
}