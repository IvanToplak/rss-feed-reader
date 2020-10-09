package agency.five.cu_it_rssfeedproject.ui.mappings

import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewData

fun FeedItem.toFeedItemViewData() = FeedItemViewData(
    id = id,
    feedId = feedId,
    title = title,
    publicationDate = publicationDate,
    link = link,
    isNew = isNew,
    isFavorite = isFavorite
)