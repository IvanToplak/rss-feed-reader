package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.di.BACKGROUND_THREAD
import agency.five.cu_it_rssfeedproject.di.MAIN_THREAD
import agency.five.cu_it_rssfeedproject.domain.interactor.AddNewFeedUseCase
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.router.Router
import android.util.Log
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.subscribeBy

private const val TAG = "NewFeedPresenter"
private const val INSERT_ERROR_MESSAGE = "Error inserting feed"

class NewFeedPresenter(
    private val router: Router,
    private val addNewFeedUseCase: AddNewFeedUseCase,
    private val schedulers: Map<String, Scheduler>
) :
    BasePresenter<NewFeedContract.View>(), NewFeedContract.Presenter {

    override fun addNewFeed(feedUrl: String) {
        if (getView() == null) return
        getView()?.showLoadingState(true)
        val subscription = addNewFeedUseCase.execute(feedUrl)
            .subscribeOn(schedulers[BACKGROUND_THREAD])
            .observeOn(schedulers[MAIN_THREAD])
            .subscribeBy(
                onComplete = {
                    getView()?.showLoadingState(false)
                    getView()?.showErrorMessage(false)
                    back()
                    router.showAllFeedsScreen()
                },
                onError = { error ->
                    getView()?.showLoadingState(false)
                    getView()?.showErrorMessage(true)
                    Log.e(
                        TAG,
                        INSERT_ERROR_MESSAGE,
                        error
                    )
                }
            )
        addDisposable(subscription)
    }

    override fun back() {
        onDestroy()
        router.hideAddNewFeedScreen()
    }
}