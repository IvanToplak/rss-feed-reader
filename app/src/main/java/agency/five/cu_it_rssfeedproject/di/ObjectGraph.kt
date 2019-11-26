package agency.five.cu_it_rssfeedproject.di

import agency.five.cu_it_rssfeedproject.app.FeedApplication
import agency.five.cu_it_rssfeedproject.data.db.database.FeedDatabase
import agency.five.cu_it_rssfeedproject.data.repository.FeedRepositoryImpl
import agency.five.cu_it_rssfeedproject.data.service.FeedServiceImpl
import agency.five.cu_it_rssfeedproject.data.service.parser.EarlFeedParserWrapper
import agency.five.cu_it_rssfeedproject.data.service.parser.FeedParserImpl
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
            FeedDatabase.NAME
        ).build()
        database = instance
        return instance
    }

    private fun getFeedParserWrapper() = EarlFeedParserWrapper()

    private fun getFeedParser() = FeedParserImpl(getFeedParserWrapper())

    private fun getFeedService() =
        FeedServiceImpl(getFeedParser())

    private fun getFeedDao() = getDatabase(FeedApplication.getAppContext()).feedDao()

    fun getFeedRepository() = FeedRepositoryImpl(getFeedDao(), getFeedService())
}