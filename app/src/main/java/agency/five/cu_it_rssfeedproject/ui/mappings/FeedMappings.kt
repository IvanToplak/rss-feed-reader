package agency.five.cu_it_rssfeedproject.ui.mappings

import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewData

fun Feed.toFeedViewData(hasUnreadItems: Boolean) = FeedViewData(
    id = id,
    title = title,
    description = description,
    url = url,
    imageUrl = imageUrl,
    hasUnreadItems = hasUnreadItems
)

fun FeedViewData.toFeed() = Feed(
    id = id,
    title = title,
    description = description,
    url = url,
    imageUrl = imageUrl
)