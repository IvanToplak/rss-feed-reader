package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedItemsUseCase
import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedItemToFeedItemViewModel

class FeedItemsPresenter(
    private var view: FeedItemsContract.View?,
    private val getFeedItemsUseCase: GetFeedItemsUseCase
) : BasePresenter<FeedItemsContract.View>(view), FeedItemsContract.Presenter {

    override fun getFeedItems(feedId: Int) {
        getFeedItemsUseCase.execute(feedId, object : FeedRepository.FeedItemsResultCallback {
            override fun onGetFeedItemsResponse(feedItems: List<FeedItem>) {
                view?.showFeedItems(feedItems.map { feedItem ->
                    mapFeedItemToFeedItemViewModel(feedItem)
                })
            }
        })
    }
}