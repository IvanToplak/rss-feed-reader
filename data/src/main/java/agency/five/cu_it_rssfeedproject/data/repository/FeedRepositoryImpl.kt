package agency.five.cu_it_rssfeedproject.data.repository

import agency.five.cu_it_rssfeedproject.data.db.dao.FeedDao
import agency.five.cu_it_rssfeedproject.data.db.partialentities.DbFeedItemIsFavorite
import agency.five.cu_it_rssfeedproject.data.db.partialentities.DbFeedItemIsNew
import agency.five.cu_it_rssfeedproject.data.mappings.*
import agency.five.cu_it_rssfeedproject.data.prefs.SharedPrefs
import agency.five.cu_it_rssfeedproject.data.service.FeedService
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import io.reactivex.Flowable

class FeedRepositoryImpl(
    private val feedDao: FeedDao,
    private val feedService: FeedService,
    private val sharedPrefs: SharedPrefs
) :
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

    override fun getFeedItems(feedId: Int) = mapDbFeedItemsToFeedItems(feedDao.getFeedItems(feedId))

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
        feedDao.getFeedIdsWithNewFeedItems().distinctUntilChanged().map { feedIds -> feedIds.toSet() }

    override fun updateFeedItemIsFavoriteStatus(feedItemId: Int, isFavorite: Boolean) =
        feedDao.updateFeedItemIsFavoriteStatus(DbFeedItemIsFavorite(feedItemId, isFavorite))

    override fun getFavoriteFeedItems() = mapDbFeedItemsToFeedItems(feedDao.getFavoriteFeedItems())

    override fun getNewFeedItemsNotificationPref() = sharedPrefs.getNewFeedItemsNotificationPref()

    override fun setNewFeedItemsNotificationPref(newFeedItemsNotificationEnabled: Boolean) =
        sharedPrefs.setNewFeedItemsNotificationPref(newFeedItemsNotificationEnabled)

    override fun getNewFeedItemsCount() = feedDao.getNewFeedItemsCount()
}