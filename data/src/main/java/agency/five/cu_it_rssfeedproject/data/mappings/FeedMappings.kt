package agency.five.cu_it_rssfeedproject.data.mappings

import agency.five.cu_it_rssfeedproject.data.db.model.DbFeed
import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeed
import agency.five.cu_it_rssfeedproject.domain.model.Feed

fun mapApiFeedToDbFeed(apiFeed: ApiFeed) = DbFeed(
    title = apiFeed.title,
    description = apiFeed.description,
    url = apiFeed.url,
    imageUrl = apiFeed.imageUrl
)

fun mapDbFeedToFeed(dbFeed: DbFeed) = Feed(
    dbFeed.id,
    dbFeed.title,
    dbFeed.description,
    dbFeed.url,
    dbFeed.imageUrl
)

fun mapFeedToDbFeed(feed: Feed) = DbFeed(
    feed.id,
    feed.title,
    feed.description,
    feed.url,
    feed.imageUrl
)