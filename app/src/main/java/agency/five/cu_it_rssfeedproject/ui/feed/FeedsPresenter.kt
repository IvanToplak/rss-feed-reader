package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedsUseCase
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedToFeedViewModel

class FeedsPresenter(
    private var view: FeedsContract.View?,
    private val getFeedsUseCase: GetFeedsUseCase
) : FeedsContract.Presenter {

    override fun getFeeds() {
        getFeedsUseCase.execute(object : FeedRepository.FeedsResultCallback {
            override fun onGetFeedsResponse(feeds: List<Feed>) {
                view?.showFeeds(feeds.map { feed -> mapFeedToFeedViewModel(feed) })
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}