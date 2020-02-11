package agency.five.cu_it_rssfeedproject.ui.common

interface ViewPresenter<T> {

    fun onCreate()

    fun onDestroy()

    fun onViewCreated(view: T?)

    fun onDestroyView()
}