package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.ui.common.BaseFragment
import agency.five.cu_it_rssfeedproject.ui.common.ScreenTitleProvider
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_feed_items.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope

private const val FEED_ID_KEY = "feedId"
private const val FEED_TITLE_KEY = "feedTitle"
private const val FAVORITE_FEED_ITEMS_KEY = "favoriteFeedItems"

class FeedItemsFragment : BaseFragment(), FeedItemsContract.View,
    FeedItemsAdapter.ListItemOnClickListener,
    FeedItemsAdapter.FavoriteButtonOnClickListener {

    private val presenter: FeedItemsContract.Presenter by currentScope.inject()
    private val screenTitleProvider: ScreenTitleProvider by inject()

    private lateinit var feedItemsAdapter: FeedItemsAdapter
    private var feedId: Int? = null
    private var feedTitle: String? = null
    private var favoriteFeedItemsOnly = false

    companion object {
        const val TAG = "feedItems"

        fun newInstance(feedId: Int, feedTitle: String) =
            FeedItemsFragment().apply {
                arguments = Bundle().apply {
                    putInt(FEED_ID_KEY, feedId)
                    putString(FEED_TITLE_KEY, feedTitle)
                }
            }

        fun newFavoriteFeedItemsInstance() = FeedItemsFragment().apply {
            arguments = Bundle().apply {
                putBoolean(FAVORITE_FEED_ITEMS_KEY, true)
            }
        }
    }

    override fun doOnCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            feedId = it.getInt(FEED_ID_KEY)
            feedTitle = it.getString(FEED_TITLE_KEY)
            favoriteFeedItemsOnly = it.getBoolean(FAVORITE_FEED_ITEMS_KEY)
        }
        presenter.onCreate()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_feed_items, container, false)

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.onViewCreated(this)
        setupRecyclerView()

        if (favoriteFeedItemsOnly) {
            getFavoriteFeedItems()
        } else if (feedId != null && feedTitle != null) {
            updateFeed(feedId!!, feedTitle!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.setGroupVisible(R.id.action_items_group, false)
    }

    override fun doOnDestroyView() {
        presenter.onDestroyView()
        if (!favoriteFeedItemsOnly) {
            screenTitleProvider.removeTitle()
        }
    }

    override fun doOnDestroy() = presenter.onDestroy()

    private fun getFavoriteFeedItems() {
        presenter.getFavoriteFeedItems()
    }

    private fun updateFeed(feedId: Int, feedTitle: String) {
        this.feedId = feedId
        updateFeedTitle(if (feedTitle.isNotEmpty()) feedTitle else getString(R.string.app_name))
        presenter.getFeedItems(feedId)
    }

    private fun updateFeedTitle(feedTitle: String) {
        this.feedTitle = feedTitle
        screenTitleProvider.addTitle(feedTitle)
    }

    private fun setupRecyclerView() {
        feedItemsAdapter = FeedItemsAdapter(mutableListOf(), this, this)

        feed_items_recycler_view.layoutManager = LinearLayoutManager(context)
        feed_items_recycler_view.adapter = feedItemsAdapter
    }

    override fun showFeedItems(feedItems: List<FeedItemViewModel>) =
        feedItemsAdapter.updateFeedItems(feedItems)

    override fun onFeedItemClicked(clickedFeedItem: FeedItemViewModel) {
        if (clickedFeedItem.link.isEmpty()) return
        if (clickedFeedItem.isNew) {
            presenter.updateFeedItemIsNewStatus(clickedFeedItem, false)
        }
        presenter.showFeedItemDetails(clickedFeedItem)
    }

    override fun onFavoriteButtonClicked(clickedFeedItem: FeedItemViewModel) =
        presenter.updateFeedItemIsFavoriteStatus(clickedFeedItem, !clickedFeedItem.isFavorite)
}
