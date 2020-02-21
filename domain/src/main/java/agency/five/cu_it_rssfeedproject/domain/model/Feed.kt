package agency.five.cu_it_rssfeedproject.domain.model

data class Feed(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val imageUrl: String = "",
    val hasUnreadItems: Boolean = false
)
