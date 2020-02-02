package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.domain.interactor.AddFeedItemsToFeedUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.DeleteFeedUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedsUseCase
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedToFeedViewModel
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedViewModelToFeed
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel
import agency.five.cu_it_rssfeedproject.ui.router.Router

class FeedsPresenter(
    private val router: Router,
    private val getFeedsUseCase: GetFeedsUseCase,
    private val deleteFeedUseCase: DeleteFeedUseCase,
    private val addFeedItemsToFeedUseCase: AddFeedItemsToFeedUseCase
) : BasePresenter<FeedsContract.View>(), FeedsContract.Presenter {

    override fun getFeeds() {
        getFeedsInternal()
    }

    private fun getFeedsInternal(getFeedItems: Boolean = true) {
        getFeedsUseCase.execute(object : FeedRepository.FeedsResultCallback {
            override fun onGetFeedsResponse(feeds: List<Feed>) {
                if (getFeedItems) {
                    addFeedItemsToFeeds(feeds)
                }
                view?.showFeeds(feeds.map { feed -> mapFeedToFeedViewModel(feed) })
            }
        })
    }

    private fun addFeedItemsToFeeds(feeds: List<Feed>) {
        feeds.forEach { feed ->
            addFeedItemsToFeedUseCase.execute(feed)
        }
    }

    override fun showAddNewFeed() {
        router.showAddNewFeedScreen()
    }

    override fun showFeedItems(feedViewModel: FeedViewModel) {
        router.showFeedItemsScreen(feedViewModel.id, feedViewModel.title)
    }

    override fun deleteFeed(feedViewModel: FeedViewModel) {
        deleteFeedUseCase.execute(
            mapFeedViewModelToFeed(feedViewModel),
            object : FeedRepository.DeleteFeedResultCallback {
                override fun onDeleteFeedResponse(success: Boolean) {
                    if (success) {
                        getFeedsInternal(false)
                    }
                }
            })
    }
}
