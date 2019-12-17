package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.domain.interactor.AddNewFeedUseCase
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import android.util.Log

class NewFeedPresenter(
    private var view: NewFeedContract.View?,
    private val addNewFeedUseCase: AddNewFeedUseCase
) : NewFeedContract.Presenter {

    override fun addNewFeed(feedUrl: String) {
        view?.showProgressBar(true)
        view?.enableAddButton(false)
        view?.showErrorMessage(false)
        addNewFeedUseCase.execute(feedUrl, object : FeedRepository.NewFeedResultCallback {
            override fun onInsertFeedResponse(success: Boolean) {
                view?.showProgressBar(false)
                view?.enableAddButton(true)
                view?.showErrorMessage(!success)
                if (success) {
                    //TODO destroy fragment and load previous fragment
                }
            }
        })
    }

    override fun onViewCreated(view: NewFeedContract.View) {
        this.view = view
    }

    override fun back() {
        //TODO -
        Log.i("BACK", "background to go back clicked!")
    }

    override fun onDestroy() {
        view = null
    }
}