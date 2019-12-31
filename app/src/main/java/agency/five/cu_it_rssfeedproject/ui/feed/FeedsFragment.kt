package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.show
import agency.five.cu_it_rssfeedproject.di.ObjectGraph
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_feeds.*

class FeedsFragment : Fragment(), FeedsContract.View, FeedsAdapter.ListItemOnLongClickListener {

    private lateinit var feedsAdapter: FeedsAdapter
    private lateinit var presenter: FeedsContract.Presenter
    private var selectedFeed: FeedViewModel = FeedViewModel()

    companion object {
        const val TAG = "feeds"
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
        presenter.getFeeds()
    }

    private fun setupPresenter() {
        presenter = ObjectGraph.getFeedsPresenter(this)
    }

    private fun setupButtons() {
        add_new_or_delete_feed_button.setOnClickListener {
            if (selectedFeed.isSelected) {
                feedsAdapter.clearSelection()
                presenter.deleteFeed(selectedFeed)
            } else {
                presenter.showAddNewFeed()
            }
        }
    }

    private fun setupRecyclerView() {
        feedsAdapter = FeedsAdapter(mutableListOf(), this)

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
        setAddNewFeedButton()
    }

    override fun updateFeeds() {
        presenter.getFeeds()
    }

    override fun onFeedSelected(selectedFeed: FeedViewModel) {
        this.selectedFeed = selectedFeed
        feedsAdapter.clearSelection()
        feedsAdapter.selectFeed(selectedFeed)
        setDeleteFeedButton()
    }
}
