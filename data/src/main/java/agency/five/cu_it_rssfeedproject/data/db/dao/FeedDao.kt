package agency.five.cu_it_rssfeedproject.data.db.dao

import agency.five.cu_it_rssfeedproject.data.db.model.DbFeed
import agency.five.cu_it_rssfeedproject.data.db.model.DbFeedItem
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
}