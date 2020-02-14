package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.ui.common.ViewPresenter
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel

interface FeedItemsContract {

    interface View {
        fun showFeedItems(feedItems: List<FeedItemViewModel>)
        fun updateFeed(feedId: Int, feedTitle: String)
        fun toggleIsNewStatus(feedItemViewModel: FeedItemViewModel)
        fun refreshFeeds()
    }

    interface Presenter : ViewPresenter<View> {
        fun getFeedItems(feedId: Int)
        fun showFeedItemDetails(feedItemViewModel: FeedItemViewModel)
        fun updateFeedItemIsNewStatus(feedItemViewModel: FeedItemViewModel, isNew: Boolean)
    }
}