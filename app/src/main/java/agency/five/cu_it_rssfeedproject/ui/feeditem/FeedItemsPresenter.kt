package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedItemsUseCase
import agency.five.cu_it_rssfeedproject.ui.common.AppSchedulers
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedItemToFeedItemViewModel
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel
import agency.five.cu_it_rssfeedproject.ui.router.Router
import android.util.Log
import io.reactivex.rxkotlin.subscribeBy

private const val TAG = "FeedItemsPresenter"
private const val GET_FEED_ITEMS_ERROR_MESSAGE = "Error retrieving feed items"

class FeedItemsPresenter(
    private val router: Router,
    private val getFeedItemsUseCase: GetFeedItemsUseCase,
    private val schedulers: AppSchedulers
) :
    BasePresenter<FeedItemsContract.View>(), FeedItemsContract.Presenter {

    override fun getFeedItems(feedId: Int) {
        val subscription = getFeedItemsUseCase.execute(feedId)
            .subscribeOn(schedulers.background())
            .observeOn(schedulers.main())
            .subscribeBy(
                onSuccess = { feedItems ->
                    withView {
                        showFeedItems(feedItems.map { feedItem ->
                            mapFeedItemToFeedItemViewModel(feedItem)
                        })
                    }
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
}