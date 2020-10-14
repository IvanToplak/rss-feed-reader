package agency.five.cu_it_rssfeedproject.ui.model

import java.util.*

data class FeedItemViewData(
    val id: Int = 0,
    val feedId: Int = 0,
    val title: String = "",
    val publicationDate: Date?,
    val link: String = "",
    val isNew: Boolean = true,
    val isFavorite: Boolean = true
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FeedItemViewData

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}