package agency.five.cu_it_rssfeedproject.ui.router

import android.view.View

interface Router {

    fun showAllFeedsScreen()

    fun showAddNewFeedScreen()

    fun hideAddNewFeedScreen()

    fun showFeedItemsScreen(feedId: Int, feedTitle: String, itemView: View)

    fun showFeedItemDetailsScreen(feedItemUrl: String, itemView: View)

    fun showFavoriteFeedItemsScreen()
}