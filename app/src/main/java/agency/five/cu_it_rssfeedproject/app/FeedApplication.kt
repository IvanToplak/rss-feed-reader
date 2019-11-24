package agency.five.cu_it_rssfeedproject.app

import agency.five.cu_it_rssfeedproject.data.db.database.FeedDatabase
import android.app.Application
import android.content.Context
import androidx.room.Room

class FeedApplication : Application() {

    companion object {
        lateinit var database: FeedDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = getDatabase(this)
    }

    private fun getDatabase(context: Context): FeedDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FeedDatabase::class.java,
            FeedDatabase.NAME
        ).build()
    }
}