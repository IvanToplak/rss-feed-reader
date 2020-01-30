package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.di.MAIN_ACTIVITY_SCOPE_ID
import agency.five.cu_it_rssfeedproject.domain.interactor.AddNewFeedUseCase
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.router.Router
import org.koin.core.KoinComponent

class NewFeedPresenter(private val addNewFeedUseCase: AddNewFeedUseCase) :
    BasePresenter<NewFeedContract.View>(), NewFeedContract.Presenter, KoinComponent {

    private val router: Router by getKoin().getScope(MAIN_ACTIVITY_SCOPE_ID).inject()

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