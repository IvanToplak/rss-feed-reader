package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.di.MAIN_ACTIVITY_SCOPE_ID
import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedItemsUseCase
import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.mappings.mapFeedItemToFeedItemViewModel
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel
import agency.five.cu_it_rssfeedproject.ui.router.Router
import org.koin.core.KoinComponent

class FeedItemsPresenter(private val getFeedItemsUseCase: GetFeedItemsUseCase) :
    BasePresenter<FeedItemsContract.View>(), FeedItemsContract.Presenter, KoinComponent {

    private val router: Router by getKoin().getScope(MAIN_ACTIVITY_SCOPE_ID).inject()

    override fun getFeedItems(feedId: Int) {
        getFeedItemsUseCase.execute(feedId, object : FeedRepository.FeedItemsResultCallback {
            override fun onGetFeedItemsResponse(feedItems: List<FeedItem>) {
                view?.showFeedItems(feedItems.map { feedItem ->
                    mapFeedItemToFeedItemViewModel(feedItem)
                })
            }
        })
    }

    override fun showFeedItemDetails(feedItemViewModel: FeedItemViewModel) {
        router.showFeedItemDetailsScreen(feedItemViewModel.link)
    }
}