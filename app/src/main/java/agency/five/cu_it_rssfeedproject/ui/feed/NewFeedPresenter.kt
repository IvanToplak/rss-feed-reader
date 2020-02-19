package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.domain.interactor.AddNewFeedUseCase
import agency.five.cu_it_rssfeedproject.ui.common.AppSchedulers
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.router.Router
import android.util.Log
import io.reactivex.rxkotlin.subscribeBy

private const val TAG = "NewFeedPresenter"
private const val INSERT_ERROR_MESSAGE = "Error inserting feed"

class NewFeedPresenter(
    private val router: Router,
    private val addNewFeedUseCase: AddNewFeedUseCase,
    private val schedulers: AppSchedulers
) :
    BasePresenter<NewFeedContract.View>(), NewFeedContract.Presenter {

    override fun addNewFeed(feedUrl: String) {
        if (!hasView()) return
        withView { showLoadingState() }
        val subscription = addNewFeedUseCase.execute(feedUrl)
            .observeOn(schedulers.main())
            .subscribeOn(schedulers.background())
            .subscribeBy(
                onComplete = {
                    withView {
                        showLoadingState(false)
                        showErrorMessage(false)
                    }
                    back()
                    router.showAllFeedsScreen()
                },
                onError = { error ->
                    withView {
                        showLoadingState(false)
                        showErrorMessage()
                    }
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