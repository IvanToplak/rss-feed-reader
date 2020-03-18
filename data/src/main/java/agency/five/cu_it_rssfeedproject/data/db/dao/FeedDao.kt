package agency.five.cu_it_rssfeedproject.data.db.dao

import agency.five.cu_it_rssfeedproject.data.db.model.DbFeed
import agency.five.cu_it_rssfeedproject.data.db.model.DbFeedItem
import agency.five.cu_it_rssfeedproject.data.db.partialentities.DbFeedItemIsFavorite
import agency.five.cu_it_rssfeedproject.data.db.partialentities.DbFeedItemIsNew
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface FeedDao {

    @Query("SELECT * FROM feed ORDER BY id ASC")
    fun getFeeds(): Flowable<List<DbFeed>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(feed: DbFeed): Completable

    @Delete
    fun delete(feed: DbFeed): Completable

    @Query("SELECT * FROM feed_item WHERE feed_id = :feedId ORDER BY publication_date DESC, id DESC")
    fun getFeedItems(feedId: Int): Flowable<List<DbFeedItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(feedItems: List<DbFeedItem>): Completable

    @Update(entity = DbFeedItem::class)
    fun updateFeedItemIsNewStatus(isNew: DbFeedItemIsNew): Completable

    @Query("SELECT COUNT(*) FROM feed_item WHERE feed_id = :feedId AND isNew = 1")
    fun getUnreadFeedItemsCount(feedId: Int): Long

    @Query("SELECT feed_id FROM feed_item WHERE isNew = 1 GROUP BY feed_id")
    fun getFeedIdsWithNewFeedItems(): Flowable<List<Int>>

    @Update(entity = DbFeedItem::class)
    fun updateFeedItemIsFavoriteStatus(isFavorite: DbFeedItemIsFavorite): Completable

    @Query("SELECT * FROM feed_item WHERE isFavorite = 1 ORDER BY feed_id ASC, publication_date DESC, id DESC")
    fun getFavoriteFeedItems(): Flowable<List<DbFeedItem>>
}