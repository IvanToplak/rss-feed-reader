package agency.five.cu_it_rssfeedproject.di

import agency.five.cu_it_rssfeedproject.data.db.database.FeedDatabase
import android.content.Context
import androidx.room.Room

object ObjectGraph {

    private var database: FeedDatabase? = null

    fun getDatabase(context: Context): FeedDatabase {
        if (database != null) {
            return database!!
        }

        val instance = Room.databaseBuilder(
            context.applicationContext,
            FeedDatabase::class.java,
            "feed_database"
        ).build()
        database = instance
        return instance
    }
}