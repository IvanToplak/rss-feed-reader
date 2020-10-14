package agency.five.cu_it_rssfeedproject.ui.router

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.ui.feed.FEED_TO_FEED_ITEMS_TRANSITION_NAME
import agency.five.cu_it_rssfeedproject.ui.feed.FeedsFragment
import agency.five.cu_it_rssfeedproject.ui.feed.NewFeedFragment
import agency.five.cu_it_rssfeedproject.ui.feeditem.FEED_ITEM_TO_DETAILS_TRANSITION_NAME
import agency.five.cu_it_rssfeedproject.ui.feeditem.FeedItemsFragment
import agency.five.cu_it_rssfeedproject.ui.feeditemdetails.FeedItemDetailsFragment
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

class RouterImpl(private val fragmentManager: FragmentManager) : Router {

    override fun showAllFeedsScreen() {
        val fragment = fragmentManager.findFragmentByTag(FeedsFragment.TAG) as? FeedsFragment
        if (fragment == null) {
            fragmentManager.commit {
                add(R.id.container_layout, FeedsFragment.newInstance(), FeedsFragment.TAG)
            }
        }
    }

    override fun showAddNewFeedScreen() {
        fragmentManager.commit {
            addToBackStack(null)
            add(R.id.container_layout, NewFeedFragment.newInstance(), NewFeedFragment.TAG)
        }
    }

    override fun hideAddNewFeedScreen() {
        val fragment = fragmentManager.findFragmentByTag(NewFeedFragment.TAG)
        if (fragment != null) {
            fragmentManager.popBackStack()
        }
    }

    override fun showFeedItemsScreen(feedId: Int, feedTitle: String, itemView: View) =
        showFeedItemsScreenInternal(feedId, feedTitle, itemView)

    override fun showFavoriteFeedItemsScreen() =
        showFeedItemsScreenInternal(showFavoritesOnly = true)

    private fun showFeedItemsScreenInternal(
        feedId: Int? = null,
        feedTitle: String? = null,
        itemView: View? = null,
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
            fragmentManager.commit {
                itemView?.let { sharedView ->
                    addSharedElement(sharedView, FEED_TO_FEED_ITEMS_TRANSITION_NAME)
                }
                addToBackStack(null)
                replace(
                    R.id.container_layout,
                    fragment,
                    FeedItemsFragment.TAG
                )
            }
        }
    }

    override fun showFeedItemDetailsScreen(feedItemUrl: String, itemView: View) {
        val feedItemDetailsFrag =
            fragmentManager.findFragmentByTag(FeedItemDetailsFragment.TAG) as? FeedItemDetailsFragment
        if (feedItemDetailsFrag == null) {
            fragmentManager.commit {
                addSharedElement(itemView, FEED_ITEM_TO_DETAILS_TRANSITION_NAME)
                addToBackStack(null)
                replace(
                    R.id.container_layout,
                    FeedItemDetailsFragment.newInstance(feedItemUrl),
                    FeedItemDetailsFragment.TAG
                )
            }
        }
    }
}