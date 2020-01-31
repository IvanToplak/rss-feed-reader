package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.domain.interactor.AddNewFeedUseCase
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.router.Router

class NewFeedPresenter(
    private val router: Router,
    private val addNewFeedUseCase: AddNewFeedUseCase
) :
    BasePresenter<NewFeedContract.View>(), NewFeedContract.Presenter {

    override fun addNewFeed(feedUrl: String) {
        if (view == null) return
        view?.showLoadingState(true)
        addNewFeedUseCase.execute(feedUrl, object : FeedRepository.NewFeedResultCallback {
            override fun onInsertFeedResponse(success: Boolean) {
                view?.showLoadingState(false)
                view?.showErrorMessage(!success)
                if (success) {
                    back()
                    router.showAllFeedsScreen()
                }
            }
        })
    }

    override fun back() {
        onDestroy()
        router.hideAddNewFeedScreen()
    }
}