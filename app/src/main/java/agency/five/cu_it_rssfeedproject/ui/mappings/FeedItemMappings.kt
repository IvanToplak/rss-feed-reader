package agency.five.cu_it_rssfeedproject.ui.mappings

import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel

fun mapFeedItemToFeedItemViewModel(feedItem: FeedItem) = FeedItemViewModel(
    feedItem.id,
    feedItem.feedId,
    feedItem.title,
    feedItem.publicationDate,
    feedItem.link,
    feedItem.isNew,
    feedItem.isFavorite
)