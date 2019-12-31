package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.di.ObjectGraph
import agency.five.cu_it_rssfeedproject.domain.interactor.DeleteFeedUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedsUseCase
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedToFeedViewModel
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedViewModelToFeed
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel

class FeedsPresenter(
    private var view: FeedsContract.View?,
    private val getFeedsUseCase: GetFeedsUseCase,
    private val deleteFeedUseCase: DeleteFeedUseCase
) : FeedsContract.Presenter {

    private val router = ObjectGraph.getScopedRouter(ObjectGraph.mainActivityScope)

    override fun getFeeds() {
        getFeedsUseCase.execute(object : FeedRepository.FeedsResultCallback {
            override fun onGetFeedsResponse(feeds: List<Feed>) {
                view?.showFeeds(feeds.map { feed -> mapFeedToFeedViewModel(feed) })
            }
        })
    }

    override fun onViewCreated(view: FeedsContract.View) {
        this.view = view
    }

    override fun showAddNewFeed() {
        router?.showAddNewFeedScreen()
    }

    override fun deleteFeed(feedViewModel: FeedViewModel) {
        deleteFeedUseCase.execute(
            mapFeedViewModelToFeed(feedViewModel),
            object : FeedRepository.DeleteFeedResultCallback {
                override fun onDeleteFeedResponse(success: Boolean) {
                    if (success) {
                        getFeeds()
                    }
                }
            })
    }

    override fun onDestroy() {
        view = null
    }
}
