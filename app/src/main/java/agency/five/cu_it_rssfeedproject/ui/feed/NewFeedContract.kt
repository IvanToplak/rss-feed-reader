package agency.five.cu_it_rssfeedproject.ui.feed

import io.reactivex.Completable

interface NewFeedContract {

    interface View

    interface ViewModel {
        fun addNewFeed(feedUrl: String): Completable
        fun getLoadingState(): Boolean
        fun setLoadingState(isLoading: Boolean)
        fun back()
    }
}