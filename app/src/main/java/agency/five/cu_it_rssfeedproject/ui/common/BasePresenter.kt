package agency.five.cu_it_rssfeedproject.ui.common

open class BasePresenter<T> : ViewPresenter<T> {

    protected var view: T? = null

    override fun onViewCreated(view: T?) {
        this.view = view
    }

    override fun onDestroy() {
        view = null
    }
}