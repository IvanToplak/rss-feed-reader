package agency.five.cu_it_rssfeedproject.ui.common

interface ViewPresenter<T> {

    fun onDestroy()

    fun onViewCreated(view: T?)
}