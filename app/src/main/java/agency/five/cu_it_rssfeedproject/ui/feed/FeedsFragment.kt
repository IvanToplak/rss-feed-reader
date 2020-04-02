package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.show
import agency.five.cu_it_rssfeedproject.ui.common.BaseFragment
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_feeds.*
import org.koin.android.viewmodel.ext.android.viewModel

class FeedsFragment : BaseFragment(), FeedsContract.View, FeedsAdapter.ListItemOnLongClickListener,
    FeedsAdapter.ListItemOnClickListener {

    private lateinit var feedsAdapter: FeedsAdapter
    private val viewModel: FeedsContract.ViewModel by viewModel<FeedsViewModel>()
    private var selectedFeed: FeedViewModel = FeedViewModel()
    private var savedSelectedFeedId: Int? = null

    companion object {
        const val TAG = "feeds"
        private const val FEED_ID_KEY = "feedId"
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

    override fun doOnSaveInstanceState(outState: Bundle) {
        if (!selectedFeed.isEmpty()) {
            outState.putInt(FEED_ID_KEY, selectedFeed.id)
        }
    }

    override fun doOnViewStateRestored(savedInstanceState: Bundle?) {
        savedSelectedFeedId = savedInstanceState?.getInt(FEED_ID_KEY)
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
                getRouter().showFavoriteFeedItemsScreen()
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
        item.icon = resources.getDrawable(
            if (viewModel.getNewFeedItemsNotificationPref())
                R.drawable.menu_notifications_active_white_24dp
            else
                R.drawable.menu_notifications_none_white_24dp,
            null
        )
    }

    private fun toggleNewFeedItemsNotification(item: MenuItem) {
        viewModel.toggleNewFeedItemsNotificationPref()
        setNewFeedItemsNotificationIcon(item)
    }

    private fun setupButtons() {
        add_new_or_delete_feed_button.setOnClickListener {
            if (!selectedFeed.isEmpty()) {
                feedsAdapter.toggleSelection(selectedFeed)
                viewModel.deleteFeed(selectedFeed)
                selectedFeed = FeedViewModel()
            } else {
                getRouter().showAddNewFeedScreen()
            }
        }
    }

    private fun setupRecyclerView() {
        feedsAdapter = FeedsAdapter(mutableListOf(), this, this)

        feeds_recycler_view.layoutManager = LinearLayoutManager(context)
        feeds_recycler_view.adapter = feedsAdapter
    }

    private fun setAddNewFeedButton() =
        add_new_or_delete_feed_button.setImageResource(R.drawable.baseline_add_white_18)

    private fun setDeleteFeedButton() =
        add_new_or_delete_feed_button.setImageResource(R.drawable.baseline_delete_white_18)

    private fun showFeeds(feeds: List<FeedViewModel>) {
        feedsAdapter.updateFeeds(feeds)
        empty_state_message_text_view?.show(feeds.isEmpty())
        if (savedSelectedFeedId != null) {
            onFeedSelected(feedsAdapter.selectFeed(savedSelectedFeedId ?: 0))
            savedSelectedFeedId = null
        } else {
            setAddNewFeedButton()
        }
    }

    override fun onFeedSelected(selectedFeed: FeedViewModel) {
        when {
            selectedFeed.isEmpty() -> setAddNewFeedButton()
            this.selectedFeed == selectedFeed -> {
                clearSelection()
            }
            else -> {
                feedsAdapter.toggleSelection(this.selectedFeed)
                if (!selectedFeed.isSelected) {
                    feedsAdapter.toggleSelection(selectedFeed)
                }
                this.selectedFeed = selectedFeed
                setDeleteFeedButton()
            }
        }
    }

    override fun onFeedClicked(clickedFeed: FeedViewModel) {
        if (clickedFeed.isEmpty()) return
        if (this.selectedFeed == clickedFeed) {
            clearSelection()
        }
        getRouter().showFeedItemsScreen(clickedFeed.id, clickedFeed.title)
    }

    private fun clearSelection() {
        feedsAdapter.toggleSelection(this.selectedFeed)
        this.selectedFeed = FeedViewModel()
        setAddNewFeedButton()
    }
}
