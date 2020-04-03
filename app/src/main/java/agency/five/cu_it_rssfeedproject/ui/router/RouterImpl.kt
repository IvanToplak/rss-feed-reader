package agency.five.cu_it_rssfeedproject.ui.router

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.ui.feed.FeedsFragment
import agency.five.cu_it_rssfeedproject.ui.feed.NewFeedFragment
import agency.five.cu_it_rssfeedproject.ui.feeditem.FeedItemsFragment
import agency.five.cu_it_rssfeedproject.ui.feeditemdetails.FeedItemDetailsFragment
import androidx.fragment.app.FragmentManager

class RouterImpl(private val fragmentManager: FragmentManager) : Router {

    override fun showAllFeedsScreen() {
        val fragment = fragmentManager.findFragmentByTag(FeedsFragment.TAG) as? FeedsFragment
        if (fragment == null) {
            fragmentManager
                .beginTransaction()
                .add(R.id.container_layout, FeedsFragment.newInstance(), FeedsFragment.TAG)
                .commit()
        }
    }

    override fun showAddNewFeedScreen() {
        fragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.container_layout, NewFeedFragment.newInstance(), NewFeedFragment.TAG)
            .commit()
    }

    override fun hideAddNewFeedScreen() {
        val fragment = fragmentManager.findFragmentByTag(NewFeedFragment.TAG)
        if (fragment != null) {
            fragmentManager.popBackStack()
        }
    }

    override fun showFeedItemsScreen(feedId: Int, feedTitle: String) =
        showFeedItemsScreenInternal(feedId, feedTitle)

    override fun showFavoriteFeedItemsScreen() =
        showFeedItemsScreenInternal(showFavoritesOnly = true)

    private fun showFeedItemsScreenInternal(
        feedId: Int? = null,
        feedTitle: String? = null,
        showFavoritesOnly: Boolean = false
    ) {
        val feedItemsFrag =
            fragmentManager.findFragmentByTag(FeedItemsFragment.TAG) as? FeedItemsFragment
        if (feedItemsFrag == null) {
            val fragment = when {
                showFavoritesOnly -> FeedItemsFragment.newFavoriteFeedItemsInstance()
                feedId != null && feedTitle != null -> FeedItemsFragment.newInstance(
                    feedId,
                    feedTitle
                )
                else -> throw IllegalArgumentException()
            }
            fragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .add(
                    R.id.container_layout,
                    fragment,
                    FeedItemsFragment.TAG
                )
                .commit()
        }
    }

    override fun showFeedItemDetailsScreen(feedItemUrl: String) {
        val feedItemDetailsFrag =
            fragmentManager.findFragmentByTag(FeedItemDetailsFragment.TAG) as? FeedItemDetailsFragment
        if (feedItemDetailsFrag == null) {
            fragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .add(
                    R.id.container_layout,
                    FeedItemDetailsFragment.newInstance(feedItemUrl),
                    FeedItemDetailsFragment.TAG
                )
                .commit()
        }
    }
}