package agency.five.cu_it_rssfeedproject.data.mappings

import agency.five.cu_it_rssfeedproject.data.db.model.DbFeedItem
import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeedItem
import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import io.reactivex.Flowable

fun ApiFeedItem.toDbFeedItem(feedId: Int) = DbFeedItem(
    feedId = feedId,
    title = title,
    publicationDate = publicationDate,
    link = link
)

fun DbFeedItem.toFeedItem() = FeedItem(
    id = id,
    feedId = feedId,
    title = title,
    publicationDate = publicationDate,
    link = link,
    isNew = isNew,
    isFavorite = isFavorite
)

fun Flowable<List<DbFeedItem>>.toFeedItems(): Flowable<List<FeedItem>> =
    map { feedItems -> feedItems.map { feedItem -> feedItem.toFeedItem() } }