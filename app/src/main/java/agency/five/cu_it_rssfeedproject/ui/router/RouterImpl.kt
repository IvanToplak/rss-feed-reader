package agency.five.cu_it_rssfeedproject.ui.router

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.ui.feed.FeedsFragment
import agency.five.cu_it_rssfeedproject.ui.feed.NewFeedFragment
import androidx.fragment.app.FragmentManager

class RouterImpl(private val fragmentManager: FragmentManager) : Router {

    override fun showAllFeedsScreen() {
        val fragment = fragmentManager.findFragmentByTag(FeedsFragment.TAG) as? FeedsFragment
        if (fragment == null) {
            fragmentManager
                .beginTransaction()
                .add(R.id.container_layout, FeedsFragment.newInstance(), FeedsFragment.TAG)
                .commit()
        } else {
            fragment.updateFeeds()
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
}