package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.ui.common.KoinFragment
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewData
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_feed_items.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val FEED_ID_KEY = "feedId"
private const val FEED_TITLE_KEY = "feedTitle"
private const val FAVORITE_FEED_ITEMS_KEY = "favoriteFeedItems"
private const val GET_FEED_ITEMS_ERROR_MESSAGE = "Error retrieving feed items"

class FeedItemsFragment : KoinFragment(), FeedItemsContract.View,
    FeedItemsAdapter.ListItemOnClickListener,
    FeedItemsAdapter.FavoriteButtonOnClickListener {

    private val viewModel: FeedItemsContract.ViewModel by viewModel<FeedItemsViewModel>()

    private lateinit var feedItemsAdapter: FeedItemsAdapter
    private lateinit var feedItemsFunctionality: Functionality

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
        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)
        arguments?.let {
            feedItemsFunctionality = if (it.getBoolean(FAVORITE_FEED_ITEMS_KEY)) {
                enterTransition = MaterialElevationScale(true)
                Functionality.FavouriteFeedItems
            } else {
                val feedId = it.getInt(FEED_ID_KEY)
                val feedTitle = it.getString(FEED_TITLE_KEY)!!
                sharedElementEnterTransition = MaterialContainerTransform()
                Functionality.FeedItems(feedId, feedTitle)
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_feed_items, container, false)

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        requestViewData()
    }

    private fun requestViewData() {
        val flowable = when (val func = feedItemsFunctionality) {
            is Functionality.FeedItems -> updateFeed(func)
            Functionality.FavouriteFeedItems -> getFavoriteFeedItems()
        }
        addDisposable(flowable.subscribeBy(
            onNext = { feedItems ->
                showFeedItems(feedItems)
            },
            onError = { error ->
                Log.e(
                    TAG,
                    GET_FEED_ITEMS_ERROR_MESSAGE,
                    error
                )
            }
        ))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.setGroupVisible(R.id.action_items_group, false)
    }

    override fun doOnDestroyView() {
        if (feedItemsFunctionality is Functionality.FeedItems) {
            removeScreenTitle()
        }
    }

    private fun getFavoriteFeedItems() = viewModel.getFavoriteFeedItems()

    private fun updateFeed(feedItems: Functionality.FeedItems): Flowable<List<FeedItemViewData>> {
        updateFeedTitle(if (feedItems.feedTitle.isNotEmpty()) feedItems.feedTitle else getString(R.string.app_name))
        return viewModel.getFeedItems(feedItems.feedId)
    }

    private fun updateFeedTitle(feedTitle: String) = addScreenTitle(feedTitle)

    private fun setupRecyclerView() {
        feedItemsAdapter = FeedItemsAdapter(mutableListOf(), this, this)

        feed_items_recycler_view.layoutManager = LinearLayoutManager(context)
        feed_items_recycler_view.adapter = feedItemsAdapter
    }

    private fun showFeedItems(feedItems: List<FeedItemViewData>) =
        feedItemsAdapter.updateFeedItems(feedItems)

    override fun onFeedItemClicked(clickedFeedItem: FeedItemViewData, clickedView: View) {
        if (clickedFeedItem.link.isEmpty()) return
        if (clickedFeedItem.isNew) {
            viewModel.updateFeedItemIsNewStatus(clickedFeedItem, false)
        }
        router.showFeedItemDetailsScreen(clickedFeedItem.link, clickedView)
    }

    override fun onFavoriteButtonClicked(clickedFeedItem: FeedItemViewData) =
        viewModel.updateFeedItemIsFavoriteStatus(clickedFeedItem, !clickedFeedItem.isFavorite)
}

sealed class Functionality {
    data class FeedItems(val feedId: Int, val feedTitle: String) : Functionality()
    object FavouriteFeedItems : Functionality()
}