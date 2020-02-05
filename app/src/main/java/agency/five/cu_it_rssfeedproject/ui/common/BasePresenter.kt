package agency.five.cu_it_rssfeedproject.ui.common

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T> : ViewPresenter<T> {

    protected var view: T? = null
    protected var compositeDisposable: CompositeDisposable? = null

    override fun onCreate() {
        compositeDisposable = CompositeDisposable()
    }

    override fun onViewCreated(view: T?) {
        this.view = view
    }

    override fun onDestroyView() {
        view = null
    }

    override fun onDestroy() {
        compositeDisposable?.dispose()
        compositeDisposable = null
    }
}