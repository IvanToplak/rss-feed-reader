package agency.five.cu_it_rssfeedproject.ui.model

data class FeedViewModel(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val imageUrl: String = "",
    var isSelected: Boolean = false,
    var hasUnreadItems: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FeedViewModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    fun isEmpty(): Boolean {
        return id == 0 && title.isEmpty() && description.isEmpty() && url.isEmpty() && imageUrl.isEmpty()
    }
}