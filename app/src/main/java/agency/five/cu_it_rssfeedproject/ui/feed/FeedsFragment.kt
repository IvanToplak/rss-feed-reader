package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.show
import agency.five.cu_it_rssfeedproject.ui.common.KoinFragment
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewData
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_feeds.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedsFragment : KoinFragment(), FeedsContract.View, FeedsAdapter.ListItemOnLongClickListener,
    FeedsAdapter.ListItemOnClickListener {

    private lateinit var feedsAdapter: FeedsAdapter
    private val viewModel: FeedsContract.ViewModel by viewModel<FeedsViewModel>()

    companion object {
        const val TAG = "feeds"
        private const val GET_FEEDS_ERROR_MESSAGE = "Error retrieving feeds"
        fun newInstance() = FeedsFragment()
    }

    override fun doOnCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_feeds, container, false)

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupButtons()
        updateFeeds()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val newFeedsNotificationItem = menu.findItem(R.id.new_feed_items_notifications_button)
        newFeedsNotificationItem?.let {
            setNewFeedItemsNotificationIcon(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_items_button -> {
                router.showFavoriteFeedItemsScreen()
                true
            }
            R.id.new_feed_items_notifications_button -> {
                toggleNewFeedItemsNotification(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateFeeds() = addDisposable(
        viewModel.getFeeds()
            .subscribeBy(
                onNext = { feedViewModels ->
                    showFeeds(feedViewModels)
                },
                onError = { error ->
                    Log.e(
                        TAG,
                        GET_FEEDS_ERROR_MESSAGE,
                        error
                    )
                })
    )

    private fun setNewFeedItemsNotificationIcon(item: MenuItem) {
        val resId = if (viewModel.getNewFeedItemsNotificationPref())
            R.drawable.menu_notifications_active_white_24dp
        else
            R.drawable.menu_notifications_none_white_24dp
        item.icon = ResourcesCompat.getDrawable(resources, resId, activity?.theme)
    }

    private fun toggleNewFeedItemsNotification(item: MenuItem) {
        viewModel.toggleNewFeedItemsNotificationPref()
        setNewFeedItemsNotificationIcon(item)
    }

    private fun setupButtons() {
        add_new_or_delete_feed_button.setOnClickListener {
            val selectedFeed = viewModel.selectedFeed
            if (selectedFeed != null) {
                feedsAdapter.toggleSelection(selectedFeed)
                viewModel.deleteFeed(selectedFeed)
                viewModel.selectedFeed = null
            } else {
                router.showAddNewFeedScreen()
            }
        }
    }

    private fun setupRecyclerView() {
        feedsAdapter = FeedsAdapter(mutableListOf(), this, this)

        feeds_recycler_view.layoutManager = LinearLayoutManager(context)
        feeds_recycler_view.adapter = feedsAdapter
    }

    private fun setAddNewFeedButton() =
        add_new_or_delete_feed_button.setImageResource(R.drawable.ic_add_18)

    private fun setDeleteFeedButton() =
        add_new_or_delete_feed_button.setImageResource(R.drawable.ic_delete_18)

    private fun showFeeds(feeds: List<FeedViewData>) {
        feedsAdapter.updateFeeds(feeds)
        empty_state_message_text_view?.show(feeds.isEmpty())
        if (viewModel.selectedFeed != null) {
            feedsAdapter.clearSelection()
            val selectedFeed = feedsAdapter.selectFeed(viewModel.selectedFeed?.id ?: 0)
            if (selectedFeed != null) {
                setDeleteFeedButton()
            } else {
                viewModel.selectedFeed = null
                setAddNewFeedButton()
            }
        } else {
            setAddNewFeedButton()
        }
    }

    override fun onFeedSelected(selectedFeed: FeedViewData) = when (viewModel.selectedFeed) {
        null -> {
            selectFeed(selectedFeed)
        }
        selectedFeed -> {
            deselectFeed(selectedFeed)
        }
        else -> {
            viewModel.selectedFeed?.let { previouslySelected ->
                feedsAdapter.toggleSelection(previouslySelected)
            }
            selectFeed(selectedFeed)
        }
    }

    override fun onFeedClicked(clickedFeed: FeedViewData) {
        viewModel.selectedFeed?.let { selectedFeed ->
            if (selectedFeed == clickedFeed) {
                deselectFeed(selectedFeed)
            }
        }
        router.showFeedItemsScreen(clickedFeed.id, clickedFeed.title)
    }

    private fun selectFeed(selectedFeed: FeedViewData) {
        viewModel.selectedFeed = selectedFeed
        feedsAdapter.toggleSelection(selectedFeed)
        setDeleteFeedButton()
    }

    private fun deselectFeed(selectedFeed: FeedViewData) {
        viewModel.selectedFeed = null
        feedsAdapter.toggleSelection(selectedFeed)
        setAddNewFeedButton()
    }
}
