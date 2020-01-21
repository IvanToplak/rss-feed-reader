package agency.five.cu_it_rssfeedproject.ui.feeditem


import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.di.ObjectGraph
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_feed_items.*

private const val FEED_ID_KEY = "feedId"
private const val FEED_TITLE_KEY = "feedTitle"

class FeedItemsFragment : Fragment(), FeedItemsContract.View,
    FeedItemsAdapter.ListItemOnClickListener {

    private lateinit var feedItemsAdapter: FeedItemsAdapter
    private lateinit var presenter: FeedItemsContract.Presenter

    private var feedId: Int? = null
    private var feedTitle: String? = null

    companion object {
        const val TAG = "feedItems"
        fun newInstance(feedId: Int, feedTitle: String) =
            FeedItemsFragment().apply {
                arguments = Bundle().apply {
                    putInt(FEED_ID_KEY, feedId)
                    putString(FEED_TITLE_KEY, feedTitle)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            feedId = it.getInt(FEED_ID_KEY)
            feedTitle = it.getString(FEED_TITLE_KEY)
        }
        setupPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        presenter.onViewCreated(this)

        if (feedId != null && feedTitle != null) {
            updateFeed(feedId!!, feedTitle!!)
        }
    }

    override fun onDestroyView() {
        ObjectGraph.getScreenTitleProvider().removeTitle()
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun updateFeed(feedId: Int, feedTitle: String) {
        this.feedId = feedId
        this.feedTitle = if (feedTitle.isNotEmpty()) feedTitle else getString(R.string.app_name)

        ObjectGraph.getScreenTitleProvider().addTitle(feedTitle)
        presenter.getFeedItems(feedId)
    }

    private fun setupPresenter() {
        presenter = ObjectGraph.getFeedItemsPresenter(this)
    }

    private fun setupRecyclerView() {
        feedItemsAdapter = FeedItemsAdapter(mutableListOf(), this)

        feed_items_recycler_view.layoutManager = LinearLayoutManager(context)
        feed_items_recycler_view.adapter = feedItemsAdapter
    }

    override fun showFeedItems(feedItems: List<FeedItemViewModel>) {
        feedItemsAdapter.updateFeedItems(feedItems)
    }

    override fun onFeedItemClicked(clickedFeedItem: FeedItemViewModel) {
        if (clickedFeedItem.link.isEmpty()) return
        presenter.showFeedItemDetails(clickedFeedItem)
    }
}
