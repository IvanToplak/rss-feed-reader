package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.ui.common.ViewPresenter

interface NewFeedContract {

    interface View {
        fun showErrorMessage(isError: Boolean = true)
        fun showLoadingState(isLoading: Boolean = true)
    }

    interface Presenter : ViewPresenter<View> {
        fun addNewFeed(feedUrl: String)
        fun back()
    }
}