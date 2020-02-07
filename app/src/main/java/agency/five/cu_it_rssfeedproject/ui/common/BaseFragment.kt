package agency.five.cu_it_rssfeedproject.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doOnCreate(savedInstanceState)
    }

    protected open fun doOnCreate(savedInstanceState: Bundle?) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doOnViewCreated(view, savedInstanceState)
    }

    protected open fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        doOnSaveInstanceState(outState)
    }

    protected open fun doOnSaveInstanceState(outState: Bundle) {}

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        doOnViewStateRestored(savedInstanceState)
    }

    protected open fun doOnViewStateRestored(savedInstanceState: Bundle?) {}

    override fun onDestroyView() {
        doOnDestroyView()
        super.onDestroyView()
    }

    protected open fun doOnDestroyView() {}

    override fun onDestroy() {
        doOnDestroy()
        super.onDestroy()
    }

    protected open fun doOnDestroy() {}
}