package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel
import androidx.fragment.app.FragmentManager

interface FeedsContract {

    interface View {
        fun showFeeds(feeds: List<FeedViewModel>)
        fun getFragManager(): FragmentManager?
    }

    interface Presenter {
        fun getFeeds()
        fun onViewCreated(view: View)
        fun showAddNewFeed()
        fun onDestroy()
    }
}