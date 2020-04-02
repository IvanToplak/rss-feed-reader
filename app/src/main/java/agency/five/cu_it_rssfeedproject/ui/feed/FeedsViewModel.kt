package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.domain.interactor.*
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.ui.common.AppSchedulers
import agency.five.cu_it_rssfeedproject.ui.common.BaseViewModel
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedToFeedViewModel
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedViewModelToFeed
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy

private const val TAG = "FeedsViewModel"
private const val DELETE_ERROR_MESSAGE = "Error deleting feed"
private const val INSERT_FEED_ITEMS_ERROR_MESSAGE = "Error inserting feed items for feed"

class FeedsViewModel(
    private val getFeedsUseCase: GetFeedsUseCase,
    private val deleteFeedUseCase: DeleteFeedUseCase,
    private val addFeedItemsToFeedUseCase: AddFeedItemsToFeedUseCase,
    private val getFeedIdsWithNewFeedItemsUseCase: GetFeedIdsWithNewFeedItemsUseCase,
    private val getNewFeedItemsNotificationPrefUseCase: GetNewFeedItemsNotificationPrefUseCase,
    private val setNewFeedItemsNotificationPrefUseCase: SetNewFeedItemsNotificationPrefUseCase,
    private val enableBackgroundFeedUpdatesUseCase: EnableBackgroundFeedUpdatesUseCase,
    private val disableBackgroundFeedUpdatesUseCase: DisableBackgroundFeedUpdatesUseCase,
    private val schedulers: AppSchedulers
) : BaseViewModel(), FeedsContract.ViewModel {

    private var feeds: Flowable<List<FeedViewModel>>? = null
    private var currentFeedCount = 0

    override fun getFeeds() = if (feeds == null) {
        fetchFeedItems()
        feeds = getFeedsInternal()
        feeds!!
    } else feeds!!

    private fun fetchFeedItems() = addDisposable(getFeedsUseCase.execute()
        .subscribeOn(schedulers.background())
        .subscribe { feeds ->
            if (feeds.isNotEmpty() && currentFeedCount <= feeds.count()) {
                addFeedItemsToFeeds(feeds)
            }
            currentFeedCount = feeds.count()
        })

    private fun getFeedsInternal() = getFeedViewModelsFlowable()
        .observeOn(schedulers.main())
        .subscribeOn(schedulers.background())

    private fun getFeedViewModelsFlowable() =
        Flowable.combineLatest<List<Feed>, Set<Int>, List<FeedViewModel>>(
            getFeedsUseCase.execute(),
            getFeedIdsWithNewFeedItemsUseCase.execute(),
            BiFunction<List<Feed>, Set<Int>, List<FeedViewModel>> { feeds, feedIdsWithNewFeed ->
                feeds.map { feed ->
                    mapFeedToFeedViewModel(feed, feedIdsWithNewFeed.contains(feed.id))
                }
            })

    private fun addFeedItemsToFeeds(feeds: List<Feed>) = feeds.forEach { feed ->
        addDisposable(
            addFeedItemsToFeedUseCase.execute(feed)
                .subscribeOn(schedulers.background())
                .subscribeBy(onError = { error ->
                    Log.e(
                        TAG,
                        INSERT_FEED_ITEMS_ERROR_MESSAGE,
                        error
                    )
                })
        )
    }

    override fun deleteFeed(feedViewModel: FeedViewModel) = addDisposable(
        deleteFeedUseCase.execute(
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
    )

    override fun toggleNewFeedItemsNotificationPref() {
        val notificationEnabled = !getNewFeedItemsNotificationPref()
        setNewFeedItemsNotificationPrefUseCase.execute(notificationEnabled)
        if (notificationEnabled) {
            enableBackgroundFeedUpdatesUseCase.execute()
        } else {
            disableBackgroundFeedUpdatesUseCase.execute()
        }
    }

    override fun getNewFeedItemsNotificationPref() =
        getNewFeedItemsNotificationPrefUseCase.execute()
}
