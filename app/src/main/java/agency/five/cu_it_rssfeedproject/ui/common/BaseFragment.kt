package agency.five.cu_it_rssfeedproject.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    private val screenTitleProvider: ScreenTitleProvider by inject()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun removeScreenTitle() = screenTitleProvider.removeTitle()

    protected fun addScreenTitle(title: String) = screenTitleProvider.addTitle(title)

    protected fun setScreenTitleVisibility(show: Boolean) = screenTitleProvider.setTitleVisibility(show)

    protected fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doOnCreate(savedInstanceState)
    }

    protected open fun doOnCreate(savedInstanceState: Bundle?) {}

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        compositeDisposable.clear()
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