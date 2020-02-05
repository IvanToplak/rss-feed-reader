package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedItemsUseCase
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedItemToFeedItemViewModel
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel
import agency.five.cu_it_rssfeedproject.ui.router.Router
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

private const val TAG = "FeedItemsPresenter"
private const val GET_FEED_ITEMS_ERROR_MESSAGE = "Error retrieving feed items"

class FeedItemsPresenter(
    private val router: Router,
    private val getFeedItemsUseCase: GetFeedItemsUseCase
) :
    BasePresenter<FeedItemsContract.View>(), FeedItemsContract.Presenter {

    override fun getFeedItems(feedId: Int) {
        val subscription = getFeedItemsUseCase.execute(feedId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { feedItems ->
                    view?.showFeedItems(feedItems.map { feedItem ->
                        mapFeedItemToFeedItemViewModel(feedItem)
                    })
                },
                onError = { error ->
                    Log.e(
                        TAG,
                        GET_FEED_ITEMS_ERROR_MESSAGE,
                        error
                    )
                }
            )
        compositeDisposable?.add(subscription)
    }

    override fun showFeedItemDetails(feedItemViewModel: FeedItemViewModel) {
        router.showFeedItemDetailsScreen(feedItemViewModel.link)
    }
}