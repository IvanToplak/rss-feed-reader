package agency.five.cu_it_rssfeedproject.ui.common

abstract class KoinActivity : BaseActivity() {

    private val screenTitleProvider: ScreenTitleProvider by inject()

    protected fun setupTitleProvider() {
        screenTitleProvider.registerSetTitleFunction { title ->
            supportActionBar?.title = title
        }

        screenTitleProvider.registerSetTitleVisibilityFunction { show ->
            if (show) {
                supportActionBar?.show()
            } else {
                supportActionBar?.hide()
            }
        }
    }

    protected fun addTitle(title: String) {
        screenTitleProvider.addTitle(title)
    }
}