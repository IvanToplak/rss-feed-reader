package agency.five.cu_it_rssfeedproject.ui.common

open class BasePresenter<T>(private var view: T?) : ViewPresenter<T> {

    override fun onViewCreated(view: T?) {
        this.view = view
    }

    override fun onDestroy() {
        view = null
    }
}