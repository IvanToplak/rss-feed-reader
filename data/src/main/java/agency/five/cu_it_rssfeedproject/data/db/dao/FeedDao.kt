package agency.five.cu_it_rssfeedproject.data.db.dao

import agency.five.cu_it_rssfeedproject.data.db.model.DbFeed
import agency.five.cu_it_rssfeedproject.data.db.model.DbFeedItem
import androidx.room.*

@Dao
interface FeedDao {

    @Query("SELECT * FROM feed ORDER BY id ASC")
    fun getFeeds(): List<DbFeed>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(feed: DbFeed)

    @Delete
    fun delete(feed: DbFeed)

    @Query("SELECT * FROM feed_item WHERE feed_id = :feedId ORDER BY publication_date DESC, id DESC")
    fun getFeedItems(feedId: Int): List<DbFeedItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(feedItems: List<DbFeedItem>)
}