package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.di.ObjectGraph
import agency.five.cu_it_rssfeedproject.domain.interactor.AddNewFeedUseCase
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter

class NewFeedPresenter(
    private var view: NewFeedContract.View?,
    private val addNewFeedUseCase: AddNewFeedUseCase
) : BasePresenter<NewFeedContract.View>(view), NewFeedContract.Presenter {

    private val router = ObjectGraph.getScopedRouter(ObjectGraph.mainActivityScope)

    override fun addNewFeed(feedUrl: String) {
        if (view == null) return
        view?.showLoadingState(true)
        addNewFeedUseCase.execute(feedUrl, object : FeedRepository.NewFeedResultCallback {
            override fun onInsertFeedResponse(success: Boolean) {
                view?.showLoadingState(false)
                view?.showErrorMessage(!success)
                if (success) {
                    back()
                    router?.showAllFeedsScreen()
                }
            }
        })
    }

    override fun back() {
        onDestroy()
        router?.hideAddNewFeedScreen()
    }
}