package agency.five.cu_it_rssfeedproject.data.mappings

import agency.five.cu_it_rssfeedproject.data.db.model.DbFeedItem
import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeedItem
import agency.five.cu_it_rssfeedproject.domain.model.FeedItem

fun mapApiFeedItemToDbFeedItem(apiFeedItem: ApiFeedItem, feedId: Int) = DbFeedItem(
    feedId = feedId,
    title = apiFeedItem.title,
    publicationDate = apiFeedItem.publicationDate,
    link = apiFeedItem.link
)

fun mapDbFeedItemToFeedItem(dbFeedItem: DbFeedItem) = FeedItem(
    dbFeedItem.id,
    dbFeedItem.feedId,
    dbFeedItem.title,
    dbFeedItem.publicationDate,
    dbFeedItem.link
)