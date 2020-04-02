package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel
import io.reactivex.Flowable

interface FeedsContract {

    interface View

    interface ViewModel {
        fun getFeeds(): Flowable<List<FeedViewModel>>
        fun deleteFeed(feedViewModel: FeedViewModel)
        fun toggleNewFeedItemsNotificationPref()
        fun getNewFeedItemsNotificationPref(): Boolean
    }
}