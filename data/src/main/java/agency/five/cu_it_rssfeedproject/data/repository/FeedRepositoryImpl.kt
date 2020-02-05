package agency.five.cu_it_rssfeedproject.data.repository

import agency.five.cu_it_rssfeedproject.data.db.dao.FeedDao
import agency.five.cu_it_rssfeedproject.data.mappings.*
import agency.five.cu_it_rssfeedproject.data.service.FeedService
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import io.reactivex.Completable
import io.reactivex.Single

class FeedRepositoryImpl(private val feedDao: FeedDao, private val feedService: FeedService) :
    FeedRepository {

    override fun insertFeed(feedUrl: String): Completable {
        return feedService.getFeed(feedUrl).flatMapCompletable { apiFeed ->
            feedDao.insert(mapApiFeedToDbFeed(apiFeed))
        }
    }

    override fun getFeeds(): Single<List<Feed>> {
        return feedDao.getFeeds().map { feeds ->
            feeds.map { feed ->
                mapDbFeedToFeed(feed)
            }
        }.first(emptyList())
    }

    override fun deleteFeed(feed: Feed): Completable {
        return feedDao.delete(mapFeedToDbFeed(feed))
    }

    override fun getFeedItems(feedId: Int): Single<List<FeedItem>> {
        return feedDao.getFeedItems(feedId).map { feedItems ->
            feedItems.map { feedItem ->
                mapDbFeedItemToFeedItem(feedItem)
            }
        }.first(emptyList())
    }

    override fun addFeedItemsToFeed(feed: Feed): Completable {
        return feedService.getFeed(feed.url).flatMapCompletable { apiFeed ->
            val bdFeedItems = apiFeed.feedItems.map { feedItem ->
                mapApiFeedItemToDbFeedItem(
                    feedItem,
                    feed.id
                )
            }
            feedDao.insert(bdFeedItems)
        }
    }
}