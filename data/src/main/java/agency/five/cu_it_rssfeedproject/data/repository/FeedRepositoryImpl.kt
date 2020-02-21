package agency.five.cu_it_rssfeedproject.data.repository

import agency.five.cu_it_rssfeedproject.data.db.dao.FeedDao
import agency.five.cu_it_rssfeedproject.data.db.partialentities.DbFeedItemIsNew
import agency.five.cu_it_rssfeedproject.data.mappings.*
import agency.five.cu_it_rssfeedproject.data.service.FeedService
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import io.reactivex.Flowable

class FeedRepositoryImpl(private val feedDao: FeedDao, private val feedService: FeedService) :
    FeedRepository {

    override fun insertFeed(feedUrl: String) =
        feedService.getFeed(feedUrl).flatMapCompletable { apiFeed ->
            feedDao.insert(mapApiFeedToDbFeed(apiFeed))
        }

    override fun getFeeds(): Flowable<List<Feed>> = feedDao.getFeeds().map { feeds ->
        feeds.map { feed ->
            mapDbFeedToFeed(feed)
        }
    }

    override fun deleteFeed(feed: Feed) = feedDao.delete(mapFeedToDbFeed(feed))

    override fun getFeedItems(feedId: Int): Flowable<List<FeedItem>> =
        feedDao.getFeedItems(feedId).map { feedItems ->
            feedItems.map { feedItem ->
                mapDbFeedItemToFeedItem(feedItem)
            }
        }

    override fun addFeedItemsToFeed(feed: Feed) =
        feedService.getFeed(feed.url).flatMapCompletable { apiFeed ->
            val bdFeedItems = apiFeed.feedItems.map { feedItem ->
                mapApiFeedItemToDbFeedItem(
                    feedItem,
                    feed.id
                )
            }
            feedDao.insert(bdFeedItems)
        }

    override fun updateFeedItemIsNewStatus(feedItemId: Int, isNew: Boolean) =
        feedDao.updateFeedItemIsNewStatus(DbFeedItemIsNew(feedItemId, isNew))

    override fun getFeedIdsWithNewFeedItems(): Flowable<Set<Int>> =
        feedDao.getFeedIdsWithNewFeedItems().map { feedIds -> feedIds.toSortedSet() }
}