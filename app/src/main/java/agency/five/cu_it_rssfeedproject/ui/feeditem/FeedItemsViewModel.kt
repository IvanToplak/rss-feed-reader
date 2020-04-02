package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.domain.interactor.GetFavoriteFeedItemsUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedItemsUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.UpdateFeedItemIsFavoriteStatusUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.UpdateFeedItemIsNewStatusUseCase
import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import agency.five.cu_it_rssfeedproject.ui.common.AppSchedulers
import agency.five.cu_it_rssfeedproject.ui.common.BaseViewModel
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedItemToFeedItemViewModel
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy

private const val TAG = "FeedItemsViewModel"
private const val UPDATE_FEED_ITEM_IS_NEW_STATUS_ERROR_MESSAGE =
    "Error updating feed item's 'is new' status"
private const val UPDATE_FEED_ITEM_IS_FAVORITE_STATUS_ERROR_MESSAGE =
    "Error updating feed item's 'is favorite' status"

class FeedItemsViewModel(
    private val getFeedItemsUseCase: GetFeedItemsUseCase,
    private val updateFeedItemIsNewStatusUseCase: UpdateFeedItemIsNewStatusUseCase,
    private val updateFeedItemIsFavoriteStatusUseCase: UpdateFeedItemIsFavoriteStatusUseCase,
    private val getFavoriteFeedItemsUseCase: GetFavoriteFeedItemsUseCase,
    private val schedulers: AppSchedulers
) :
    BaseViewModel(), FeedItemsContract.ViewModel {

    private var feedItems: Flowable<List<FeedItemViewModel>>? = null
    private var favoriteFeedItems: Flowable<List<FeedItemViewModel>>? = null

    override fun getFeedItems(feedId: Int) =
        feedItems ?: getFeedItemsSubscription(getFeedItemsUseCase.execute(feedId))


    override fun getFavoriteFeedItems() =
        favoriteFeedItems ?: getFeedItemsSubscription(getFavoriteFeedItemsUseCase.execute())

    private fun getFeedItemsSubscription(flowable: Flowable<List<FeedItem>>) =
        flowable.map { feedItems ->
            feedItems.map { feedItem ->
                mapFeedItemToFeedItemViewModel(feedItem)
            }
        }
            .observeOn(schedulers.main())
            .subscribeOn(schedulers.background())

    override fun updateFeedItemIsNewStatus(feedItemViewModel: FeedItemViewModel, isNew: Boolean) =
        addDisposable(
            updateFeedItemIsNewStatusUseCase.execute(feedItemViewModel.id, isNew)
                .subscribeOn(schedulers.background())
                .subscribeBy(
                    onError = { error ->
                        Log.e(
                            TAG,
                            UPDATE_FEED_ITEM_IS_NEW_STATUS_ERROR_MESSAGE,
                            error
                        )
                    })
        )

    override fun updateFeedItemIsFavoriteStatus(
        feedItemViewModel: FeedItemViewModel,
        isFavorite: Boolean
    ) = addDisposable(
        updateFeedItemIsFavoriteStatusUseCase.execute(feedItemViewModel.id, isFavorite)
            .subscribeOn(schedulers.background())
            .subscribeBy(
                onError = { error ->
                    Log.e(
                        TAG,
                        UPDATE_FEED_ITEM_IS_FAVORITE_STATUS_ERROR_MESSAGE,
                        error
                    )
                })
    )
}