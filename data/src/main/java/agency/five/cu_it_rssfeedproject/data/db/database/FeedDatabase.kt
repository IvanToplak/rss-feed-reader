package agency.five.cu_it_rssfeedproject.data.db.database

import agency.five.cu_it_rssfeedproject.data.db.dao.FeedDao
import agency.five.cu_it_rssfeedproject.data.db.database.FeedDatabase.Companion.VERSION
import agency.five.cu_it_rssfeedproject.data.db.model.DbFeed
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DbFeed::class], version = VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FeedDatabase : RoomDatabase() {

    companion object {
        const val NAME = "feed_database"
        const val VERSION = 1
    }

    abstract fun feedDao(): FeedDao
}