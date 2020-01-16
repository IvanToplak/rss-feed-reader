package agency.five.cu_it_rssfeedproject.ui.common

import java.util.*

object ScreenTitleProviderImpl : ScreenTitleProvider {

    private val titles: Deque<String> = ArrayDeque<String>()
    private var setTitle: ((String) -> Unit)? = null

    override fun addTitle(title: String) {
        if (titles.peek() != title) {
            titles.push(title)
            setTitle?.invoke(title)
        }
    }

    override fun removeTitle() {
        titles.peek()?.let {
            titles.pop()
            titles.peek()?.let { title ->
                setTitle?.invoke(title)
            }
        }
    }

    override fun registerSetTitleFunction(setTitle: (String) -> Unit) {
        this.setTitle = setTitle
    }
}