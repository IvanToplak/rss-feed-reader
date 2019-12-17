package agency.five.cu_it_rssfeedproject.ui.feed

interface NewFeedContract {

    interface View {
        fun showErrorMessage(show: Boolean)
        fun showProgressBar(show: Boolean)
        fun enableAddButton(enable: Boolean)
    }

    interface Presenter {
        fun addNewFeed(feedUrl: String)
        fun onViewCreated(view: View)
        fun back()
        fun onDestroy()
    }
}