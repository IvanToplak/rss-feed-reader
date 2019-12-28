package agency.five.cu_it_rssfeedproject.ui.feed

interface NewFeedContract {

    interface View {
        fun showErrorMessage(isError: Boolean)
        fun showLoadingState(isLoading: Boolean)
    }

    interface Presenter {
        fun addNewFeed(feedUrl: String)
        fun onViewCreated(view: View)
        fun back()
        fun onDestroy()
    }
}