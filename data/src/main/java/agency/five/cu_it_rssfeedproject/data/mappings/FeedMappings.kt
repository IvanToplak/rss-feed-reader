package agency.five.cu_it_rssfeedproject.data.mappings

import agency.five.cu_it_rssfeedproject.data.db.model.DbFeed
import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeed
import agency.five.cu_it_rssfeedproject.domain.model.Feed

fun ApiFeed.toDbFeed() = DbFeed(
    title = title,
    description = description,
    url = url,
    imageUrl = imageUrl
)

fun DbFeed.toFeed() = Feed(
    id = id,
    title = title,
    description = description,
    url = url,
    imageUrl = imageUrl
)

fun Feed.toDbFeed() = DbFeed(
    id = id,
    title = title,
    description = description,
    url = url,
    imageUrl = imageUrl
)