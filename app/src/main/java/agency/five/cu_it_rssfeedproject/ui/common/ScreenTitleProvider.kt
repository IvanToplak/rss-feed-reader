package agency.five.cu_it_rssfeedproject.ui.common

interface ScreenTitleProvider {

    fun addTitle(title: String)

    fun removeTitle()

    fun registerSetTitleFunction(setTitle : (String) -> Unit)
}