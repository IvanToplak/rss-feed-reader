package agency.five.cu_it_rssfeedproject.data.db.dao

import agency.five.cu_it_rssfeedproject.data.db.model.DbFeed
import androidx.room.*

@Dao
interface FeedDao {

    @Query("SELECT * FROM feed ORDER BY id ASC")
    fun getFeeds(): List<DbFeed>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(feed: DbFeed)

    @Delete
    fun delete(feed: DbFeed)
}