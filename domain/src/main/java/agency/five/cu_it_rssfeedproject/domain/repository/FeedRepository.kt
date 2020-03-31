package agency.five.cu_it_rssfeedproject.domain.repository

import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import io.reactivex.Completable
import io.reactivex.Flowable

interface FeedRepository {

    fun insertFeed(feedUrl: String): Completable

    fun getFeeds(): Flowable<List<Feed>>

    fun deleteFeed(feed: Feed): Completable

    fun getFeedItems(feedId: Int): Flowable<List<FeedItem>>

    fun addFeedItemsToFeed(feed: Feed): Completable

    fun updateFeedItemIsNewStatus(feedItemId: Int, isNew: Boolean): Completable

    fun getFeedIdsWithNewFeedItems(): Flowable<Set<Int>>

    fun updateFeedItemIsFavoriteStatus(feedItemId: Int, isFavorite: Boolean): Completable

    fun getFavoriteFeedItems(): Flowable<List<FeedItem>>

    fun getNewFeedItemsNotificationPref(): Boolean

    fun setNewFeedItemsNotificationPref(newFeedItemsNotificationEnabled: Boolean)

    fun getNewFeedItemsCount(): Flowable<Long>
}
