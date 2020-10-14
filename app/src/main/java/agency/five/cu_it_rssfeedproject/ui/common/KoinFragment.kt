package agency.five.cu_it_rssfeedproject.ui.common

abstract class KoinFragment : BaseFragment() {

    private val screenTitleProvider: ScreenTitleProvider by inject()

    protected fun removeScreenTitle() = screenTitleProvider.removeTitle()

    protected fun addScreenTitle(title: String) = screenTitleProvider.addTitle(title)

    protected fun setScreenTitleVisibility(show: Boolean) =
        screenTitleProvider.setTitleVisibility(show)
}