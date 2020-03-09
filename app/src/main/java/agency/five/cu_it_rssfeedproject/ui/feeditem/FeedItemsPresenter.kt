package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedItemsUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.UpdateFeedItemIsFavoriteStatusUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.UpdateFeedItemIsNewStatusUseCase
import agency.five.cu_it_rssfeedproject.ui.common.AppSchedulers
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedItemToFeedItemViewModel
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel
import agency.five.cu_it_rssfeedproject.ui.router.Router
import android.util.Log
import io.reactivex.rxkotlin.subscribeBy

private const val TAG = "FeedItemsPresenter"
private const val GET_FEED_ITEMS_ERROR_MESSAGE = "Error retrieving feed items"
private const val UPDATE_FEED_ITEM_IS_NEW_STATUS_ERROR_MESSAGE =
    "Error updating feed item's 'is new' status"
private const val UPDATE_FEED_ITEM_IS_FAVORITE_STATUS_ERROR_MESSAGE =
    "Error updating feed item's 'is favorite' status"

class FeedItemsPresenter(
    private val router: Router,
    private val getFeedItemsUseCase: GetFeedItemsUseCase,
    private val updateFeedItemIsNewStatusUseCase: UpdateFeedItemIsNewStatusUseCase,
    private val updateFeedItemIsFavoriteStatusUseCase: UpdateFeedItemIsFavoriteStatusUseCase,
    private val schedulers: AppSchedulers
) :
    BasePresenter<FeedItemsContract.View>(), FeedItemsContract.Presenter {

    override fun getFeedItems(feedId: Int) {
        val subscription = getFeedItemsUseCase.execute(feedId)
            .map { feedItems -> feedItems.map { feedItem -> mapFeedItemToFeedItemViewModel(feedItem) } }
            .observeOn(schedulers.main())
            .subscribeOn(schedulers.background())
            .subscribeBy(
                onNext = { feedItems ->
                    withView { showFeedItems(feedItems) }
                },
                onError = { error ->
                    Log.e(
                        TAG,
                        GET_FEED_ITEMS_ERROR_MESSAGE,
                        error
                    )
                }
            )
        addDisposable(subscription)
    }

    override fun showFeedItemDetails(feedItemViewModel: FeedItemViewModel) {
        router.showFeedItemDetailsScreen(feedItemViewModel.link)
    }

    override fun updateFeedItemIsNewStatus(feedItemViewModel: FeedItemViewModel, isNew: Boolean) {
        val subscription =
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
        addDisposable(subscription)
    }

    override fun updateFeedItemIsFavoriteStatus(
        feedItemViewModel: FeedItemViewModel,
        isFavorite: Boolean
    ) {
        val subscription =
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
        addDisposable(subscription)
    }
}