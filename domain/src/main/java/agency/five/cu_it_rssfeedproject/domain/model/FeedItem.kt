package agency.five.cu_it_rssfeedproject.domain.model

import java.util.*

data class FeedItem(
    val id: Int = 0,
    val feedId: Int = 0,
    val title: String = "",
    val publicationDate: Date?,
    val link: String = "",
    val isNew: Boolean = true
)
