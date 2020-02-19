package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.domain.interactor.AddFeedItemsToFeedUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.DeleteFeedUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedHasUnreadItemsStatusUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedsUseCase
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.ui.common.AppSchedulers
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.common.FeedIsNewStatusChangedEvent
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedToFeedViewModel
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedViewModelToFeed
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel
import agency.five.cu_it_rssfeedproject.ui.router.Router
import android.util.Log
import io.reactivex.rxkotlin.subscribeBy

private const val TAG = "FeedsPresenter"
private const val GET_FEEDS_ERROR_MESSAGE = "Error retrieving feeds"
private const val DELETE_ERROR_MESSAGE = "Error deleting feed"
private const val INSERT_FEED_ITEMS_ERROR_MESSAGE = "Error inserting feed items for feed"
private const val GET_FEED_ITEMS_UNREAD_STATUS_ERROR_MESSAGE =
    "Error while retrieving feed items unread status"

class FeedsPresenter(
    private val router: Router,
    private val getFeedsUseCase: GetFeedsUseCase,
    private val deleteFeedUseCase: DeleteFeedUseCase,
    private val addFeedItemsToFeedUseCase: AddFeedItemsToFeedUseCase,
    private val getFeedHasUnreadItemsStatusUseCase: GetFeedHasUnreadItemsStatusUseCase,
    private val schedulers: AppSchedulers,
    private val feedIsNewStatusChangedEvent: FeedIsNewStatusChangedEvent
) : BasePresenter<FeedsContract.View>(), FeedsContract.Presenter {

    override fun getFeeds() {
        getFeedsInternal()
    }

    private fun getFeedsInternal(getFeedItems: Boolean = true) {
        val subscription = getFeedsUseCase.execute()
            .doOnSuccess { feeds ->
                if (getFeedItems) {
                    addFeedItemsToFeeds(feeds)
                }
            }
            .observeOn(schedulers.main())
            .subscribeOn(schedulers.background())
            .subscribeBy(
                onSuccess = { feeds ->
                    withView { showFeeds(feeds.map { feed -> mapFeedToFeedViewModel(feed) }) }
                },
                onError = { error ->
                    Log.e(
                        TAG,
                        GET_FEEDS_ERROR_MESSAGE,
                        error
                    )
                })
        addDisposable(subscription)
    }

    private fun addFeedItemsToFeeds(feeds: List<Feed>) {
        feeds.forEach { feed ->
            val subscription = addFeedItemsToFeedUseCase.execute(feed)
                .doOnEvent {
                    getFeedHasUnreadItemsStatus(feed)
                }
                .subscribeOn(schedulers.background())
                .subscribeBy(onError = { error ->
                    Log.e(
                        TAG,
                        INSERT_FEED_ITEMS_ERROR_MESSAGE,
                        error
                    )
                })
            addDisposable(subscription)
        }
    }

    private fun getFeedHasUnreadItemsStatus(feed: Feed) {
        val subscription = getFeedHasUnreadItemsStatusUseCase.execute(feed.id)
            .observeOn(schedulers.main())
            .subscribeOn(schedulers.background())
            .subscribeBy(
                onSuccess = { hasUnreadItems ->
                    withView {
                        setNewFeedItemsIndicator(
                            mapFeedToFeedViewModel(feed),
                            hasUnreadItems
                        )
                    }
                },
                onError = { error ->
                    Log.e(
                        TAG,
                        GET_FEED_ITEMS_UNREAD_STATUS_ERROR_MESSAGE,
                        error
                    )
                })
        addDisposable(subscription)
    }

    override fun showAddNewFeed() {
        router.showAddNewFeedScreen()
    }

    override fun showFeedItems(feedViewModel: FeedViewModel) {
        router.showFeedItemsScreen(feedViewModel.id, feedViewModel.title)
    }

    override fun deleteFeed(feedViewModel: FeedViewModel) {
        val subscription = deleteFeedUseCase.execute(
            mapFeedViewModelToFeed(feedViewModel)
        ).subscribeOn(schedulers.background())
            .subscribeBy(
                onComplete = { getFeedsInternal(false) },
                onError = { error ->
                    Log.e(
                        TAG,
                        DELETE_ERROR_MESSAGE,
                        error
                    )
                })
        addDisposable(subscription)
    }

    override fun subscribeToFeedIsNewStatusChangedEvent() {
        val subscription = feedIsNewStatusChangedEvent.subscribe()
            .observeOn(schedulers.background())
            .subscribeOn(schedulers.background())
            .subscribe {
                getFeeds()
            }
        addDisposable(subscription)
    }
}
