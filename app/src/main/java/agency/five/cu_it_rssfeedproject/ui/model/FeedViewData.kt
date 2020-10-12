package agency.five.cu_it_rssfeedproject.ui.model

data class FeedViewData(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val imageUrl: String = "",
    var isSelected: Boolean = false,
    val hasUnreadItems: Boolean = true
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FeedViewData

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}