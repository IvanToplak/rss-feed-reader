package agency.five.cu_it_rssfeedproject.domain.repository

import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import io.reactivex.Completable
import io.reactivex.Single

interface FeedRepository {

    fun insertFeed(feedUrl: String): Completable

    fun getFeeds(): Single<List<Feed>>

    fun deleteFeed(feed: Feed): Completable

    fun getFeedItems(feedId: Int): Single<List<FeedItem>>

    fun addFeedItemsToFeed(feed: Feed): Completable

    fun updateFeedItemIsNewStatus(feedItemId: Int, isNew: Boolean): Completable

    fun getFeedHasUnreadItemsStatus(feedId: Int): Single<Boolean>
}