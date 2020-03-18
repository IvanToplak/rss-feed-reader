package agency.five.cu_it_rssfeedproject.ui.router

interface Router {

    fun showAllFeedsScreen()

    fun showAddNewFeedScreen()

    fun hideAddNewFeedScreen()

    fun showFeedItemsScreen(feedId: Int, feedTitle: String)

    fun showFeedItemDetailsScreen(feedItemUrl: String)

    fun showFavoriteFeedItems()
}