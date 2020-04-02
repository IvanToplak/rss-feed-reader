package agency.five.cu_it_rssfeedproject.ui.common

import agency.five.cu_it_rssfeedproject.di.MAIN_ACTIVITY_SCOPE_ID
import agency.five.cu_it_rssfeedproject.ui.router.Router
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    private val _screenTitleProvider: ScreenTitleProvider by inject()
    private val _router: Router by getKoin().getScope(MAIN_ACTIVITY_SCOPE_ID).inject()

    private var compositeDisposable: CompositeDisposable? = null

    protected fun getScreenTitleProvider() = _screenTitleProvider

    protected fun getRouter() = _router

    protected fun addDisposable(disposable: Disposable) = compositeDisposable?.add(disposable)

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doOnCreate(savedInstanceState)
    }

    protected open fun doOnCreate(savedInstanceState: Bundle?) {}

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()
        doOnViewCreated(view, savedInstanceState)
    }

    protected open fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {}

    final override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        doOnSaveInstanceState(outState)
    }

    protected open fun doOnSaveInstanceState(outState: Bundle) {}

    final override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        doOnViewStateRestored(savedInstanceState)
    }

    protected open fun doOnViewStateRestored(savedInstanceState: Bundle?) {}

    override fun onDestroyView() {
        compositeDisposable?.dispose()
        compositeDisposable = null
        doOnDestroyView()
        super.onDestroyView()
    }

    protected open fun doOnDestroyView() {}

    final override fun onDestroy() {
        doOnDestroy()
        super.onDestroy()
    }

    protected open fun doOnDestroy() {}
}