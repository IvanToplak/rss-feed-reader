package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.ui.common.ViewPresenter
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel

interface FeedsContract {

    interface View {
        fun showFeeds(feeds: List<FeedViewModel>)
        fun updateFeeds()
        fun setNewFeedItemsIndicator(feedViewModel: FeedViewModel, hasUnreadItems: Boolean)
    }

    interface Presenter : ViewPresenter<View> {
        fun getFeeds()
        fun showAddNewFeed()
        fun showFeedItems(feedViewModel: FeedViewModel)
        fun deleteFeed(feedViewModel: FeedViewModel)
    }
}