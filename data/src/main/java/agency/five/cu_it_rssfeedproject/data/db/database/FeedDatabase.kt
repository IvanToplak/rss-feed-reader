package agency.five.cu_it_rssfeedproject.data.db.database

import agency.five.cu_it_rssfeedproject.data.db.dao.FeedDao
import agency.five.cu_it_rssfeedproject.data.db.model.DbFeed
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DbFeed::class], version = 1, exportSchema = false)
abstract class FeedDatabase : RoomDatabase() {

    abstract fun feedDao(): FeedDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FeedDatabase? = null

        fun getDatabase(context: Context): FeedDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FeedDatabase::class.java,
                    "feed_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}