package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel

interface FeedItemsContract {

    interface View {
        fun showFeedItems(feedItems: List<FeedItemViewModel>)
        fun updateFeed(feedId: Int, feedTitle: String)
    }

    interface Presenter {
        fun getFeedItems(feedId: Int)
        fun onViewCreated(view: View)
        fun onDestroy()
    }
}