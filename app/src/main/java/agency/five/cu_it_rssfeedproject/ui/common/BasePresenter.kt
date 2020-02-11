package agency.five.cu_it_rssfeedproject.ui.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T> : ViewPresenter<T> {

    private var view: T? = null
    private var compositeDisposable: CompositeDisposable? = null

    protected fun hasView() = view != null

    protected fun withView(action: T.() -> Unit) = view?.let(action)

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable?.add(disposable)
    }

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