package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.domain.interactor.AddNewFeedUseCase
import agency.five.cu_it_rssfeedproject.ui.common.BasePresenter
import agency.five.cu_it_rssfeedproject.ui.router.Router
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

private const val TAG = "NewFeedPresenter"
private const val INSERT_ERROR_MESSAGE = "Error inserting feed"

class NewFeedPresenter(
    private val router: Router,
    private val addNewFeedUseCase: AddNewFeedUseCase
) :
    BasePresenter<NewFeedContract.View>(), NewFeedContract.Presenter {

    override fun addNewFeed(feedUrl: String) {
        if (view == null) return
        view?.showLoadingState(true)
        val subscription = addNewFeedUseCase.execute(feedUrl)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    view?.showLoadingState(false)
                    view?.showErrorMessage(false)
                    back()
                    router.showAllFeedsScreen()
                },
                onError = { error ->
                    view?.showLoadingState(false)
                    view?.showErrorMessage(true)
                    Log.e(
                        TAG,
                        INSERT_ERROR_MESSAGE,
                        error
                    )
                }
            )
        compositeDisposable?.add(subscription)
    }

    override fun back() {
        onDestroy()
        router.hideAddNewFeedScreen()
    }
}