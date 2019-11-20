package agency.five.cu_it_rssfeedproject.data.db.database

import agency.five.cu_it_rssfeedproject.data.db.dao.FeedDao
import agency.five.cu_it_rssfeedproject.data.db.model.DbFeed
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbFeed::class], version = 1, exportSchema = false)
abstract class FeedDatabase : RoomDatabase() {

    abstract fun feedDao(): FeedDao
}