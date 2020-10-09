package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.domain.interactor.*
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.ui.common.AppSchedulers
import agency.five.cu_it_rssfeedproject.ui.common.BaseViewModel
import agency.five.cu_it_rssfeedproject.ui.mappings.toFeed
import agency.five.cu_it_rssfeedproject.ui.mappings.toFeedViewData
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewData
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

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

    private var feeds: Flowable<List<FeedViewData>> = getFeedsInternal()
        .doOnSubscribe { fetchFeedItems() }
        .replay(1)
        .refCount(1000, TimeUnit.MILLISECONDS)

    private var currentFeedCount = 0

    override fun getFeeds() = feeds

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
        Flowable.combineLatest(
            getFeedsUseCase.execute(),
            getFeedIdsWithNewFeedItemsUseCase.execute(),
            { feeds, feedIdsWithNewFeed ->
                feeds.map { feed -> feed.toFeedViewData(feedIdsWithNewFeed.contains(feed.id)) }
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

    override fun deleteFeed(feedViewData: FeedViewData) = addDisposable(
        deleteFeedUseCase.execute(feedViewData.toFeed())
            .subscribeOn(schedulers.background())
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
