package agency.five.cu_it_rssfeedproject.data.service.model

data class ApiFeed(
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val imageUrl: String = "",
    val feedItems: List<ApiFeedItem> = emptyList()
)