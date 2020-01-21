package agency.five.cu_it_rssfeedproject.ui.model

import java.util.*

data class FeedItemViewModel(
    val id: Int = 0,
    val feedId: Int = 0,
    val title: String = "",
    val publicationDate: Date?,
    val link: String = ""
)