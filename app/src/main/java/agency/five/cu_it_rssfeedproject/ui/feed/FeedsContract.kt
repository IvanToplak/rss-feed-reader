package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel

interface FeedsContract {

    interface View {
        fun showFeeds(feeds: List<FeedViewModel>)
        fun updateFeeds()
    }

    interface Presenter {
        fun getFeeds()
        fun onViewCreated(view: View)
        fun showAddNewFeed()
        fun deleteFeed(feedViewModel: FeedViewModel)
        fun onDestroy()
    }
}