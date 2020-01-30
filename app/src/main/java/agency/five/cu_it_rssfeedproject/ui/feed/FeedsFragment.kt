package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.show
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_feeds.*
import org.koin.androidx.scope.currentScope

class FeedsFragment : Fragment(), FeedsContract.View, FeedsAdapter.ListItemOnLongClickListener,
    FeedsAdapter.ListItemOnClickListener {

    private lateinit var feedsAdapter: FeedsAdapter
    private val presenter: FeedsContract.Presenter by currentScope.inject()
    private var selectedFeed: FeedViewModel = FeedViewModel()
    private var savedSelectedFeedId: Int? = null

    companion object {
        const val TAG = "feeds"
        const val FEED_ID_KEY = "feedId"
        fun newInstance() = FeedsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feeds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupButtons()
        presenter.onViewCreated(this)
        updateFeeds()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!selectedFeed.isEmpty()) {
            outState.putInt(FEED_ID_KEY, selectedFeed.id)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedSelectedFeedId = savedInstanceState?.getInt(FEED_ID_KEY)
    }

    private fun setupPresenter() {
        presenter.onViewCreated(this)
    }

    private fun setupButtons() {
        add_new_or_delete_feed_button.setOnClickListener {
            if (!selectedFeed.isEmpty()) {
                feedsAdapter.toggleSelection(selectedFeed)
                presenter.deleteFeed(selectedFeed)
                selectedFeed = FeedViewModel()
            } else {
                presenter.showAddNewFeed()
            }
        }
    }

    private fun setupRecyclerView() {
        feedsAdapter = FeedsAdapter(mutableListOf(), this, this)

        feeds_recycler_view.layoutManager = LinearLayoutManager(context)
        feeds_recycler_view.adapter = feedsAdapter
    }

    private fun setAddNewFeedButton() {
        add_new_or_delete_feed_button.setImageResource(R.drawable.baseline_add_white_18)
    }

    private fun setDeleteFeedButton() {
        add_new_or_delete_feed_button.setImageResource(R.drawable.baseline_delete_white_18)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showFeeds(feeds: List<FeedViewModel>) {
        feedsAdapter.updateFeeds(feeds)
        empty_state_message_text_view?.show(feeds.isEmpty())
        if (savedSelectedFeedId != null) {
            onFeedSelected(feedsAdapter.selectFeed(savedSelectedFeedId ?: 0))
            savedSelectedFeedId = null
        } else {
            setAddNewFeedButton()
        }
    }

    override fun updateFeeds() {
        presenter.getFeeds()
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
        presenter.showFeedItems(clickedFeed)
    }

    private fun clearSelection() {
        feedsAdapter.toggleSelection(this.selectedFeed)
        this.selectedFeed = FeedViewModel()
        setAddNewFeedButton()
    }
}
