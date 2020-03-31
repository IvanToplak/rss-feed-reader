package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.domain.interactor.*
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.ui.common.AppSchedulers
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedToFeedViewModel
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedViewModelToFeed
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel
import agency.five.cu_it_rssfeedproject.ui.router.Router
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy

private const val TAG = "FeedsPresenter"
private const val GET_FEEDS_ERROR_MESSAGE = "Error retrieving feeds"
private const val DELETE_ERROR_MESSAGE = "Error deleting feed"
private const val INSERT_FEED_ITEMS_ERROR_MESSAGE = "Error inserting feed items for feed"

class FeedsPresenter(
    private val router: Router,
    private val getFeedsUseCase: GetFeedsUseCase,
    private val deleteFeedUseCase: DeleteFeedUseCase,
    private val addFeedItemsToFeedUseCase: AddFeedItemsToFeedUseCase,
    private val getFeedIdsWithNewFeedItemsUseCase: GetFeedIdsWithNewFeedItemsUseCase,
    private val getNewFeedItemsNotificationPrefUseCase: GetNewFeedItemsNotificationPrefUseCase,
    private val setNewFeedItemsNotificationPrefUseCase: SetNewFeedItemsNotificationPrefUseCase,
    private val enableBackgroundFeedUpdatesUseCase: EnableBackgroundFeedUpdatesUseCase,
    private val disableBackgroundFeedUpdatesUseCase: DisableBackgroundFeedUpdatesUseCase,
    private val schedulers: AppSchedulers
) : BasePresenter<FeedsContract.View>(), FeedsContract.Presenter {

    private var currentFeedCount = 0

    override fun getFeeds() {
        fetchFeedItems()
        getFeedsInternal()
    }

    private fun fetchFeedItems() {
        val subscription = getFeedsUseCase.execute()
            .subscribeOn(schedulers.background())
            .subscribe { feeds ->
                if (feeds.isNotEmpty() && currentFeedCount <= feeds.count()) {
                    addFeedItemsToFeeds(feeds)
                }
                currentFeedCount = feeds.count()
            }
        addDisposable(subscription)
    }

    private fun getFeedsInternal() {
        val subscription =
            getFeedViewModelsFlowable()
                .observeOn(schedulers.main())
                .subscribeOn(schedulers.background())
                .subscribeBy(
                    onNext = { feedViewModels ->
                        withView { showFeeds(feedViewModels) }
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

    private fun getFeedViewModelsFlowable() =
        Flowable.combineLatest<List<Feed>, Set<Int>, List<FeedViewModel>>(
            getFeedsUseCase.execute(),
            getFeedIdsWithNewFeedItemsUseCase.execute(),
            BiFunction<List<Feed>, Set<Int>, List<FeedViewModel>> { feeds, feedIdsWithNewFeed ->
                feeds.map { feed ->
                    mapFeedToFeedViewModel(feed, feedIdsWithNewFeed.contains(feed.id))
                }
            })

    private fun addFeedItemsToFeeds(feeds: List<Feed>) {
        feeds.forEach { feed ->
            val subscription = addFeedItemsToFeedUseCase.execute(feed)
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

    override fun showAddNewFeed() = router.showAddNewFeedScreen()

    override fun showFeedItems(feedViewModel: FeedViewModel) =
        router.showFeedItemsScreen(feedViewModel.id, feedViewModel.title)

    override fun showFavoriteFeedItems() = router.showFavoriteFeedItems()

    override fun deleteFeed(feedViewModel: FeedViewModel) {
        val subscription = deleteFeedUseCase.execute(
            mapFeedViewModelToFeed(feedViewModel)
        ).subscribeOn(schedulers.background())
            .subscribeBy(
                onError = { error ->
                    Log.e(
                        TAG,
                        DELETE_ERROR_MESSAGE,
                        error
                    )
                })
        addDisposable(subscription)
    }

    override fun toggleNewFeedItemsNotificationPref() {
        val notificationEnabled = !getNewFeedItemsNotificationPref()
        setNewFeedItemsNotificationPrefUseCase.execute(notificationEnabled)
        if (notificationEnabled) {
            enableBackgroundFeedUpdatesUseCase.execute()
        } else {
            disableBackgroundFeedUpdatesUseCase.execute()
        }
    }

    override fun getNewFeedItemsNotificationPref(): Boolean =
        getNewFeedItemsNotificationPrefUseCase.execute()
}
