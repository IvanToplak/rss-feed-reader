package agency.five.cu_it_rssfeedproject.app

import agency.five.cu_it_rssfeedproject.di.ObjectGraph
import android.app.Application
import android.content.Context

class FeedApplication : Application() {

    companion object {
        private lateinit var instance: FeedApplication

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()

        ObjectGraph.getDatabase(getAppContext())

        //uncomment to test
        //loadTestFeeds()
    }

    private fun loadTestFeeds() {
        val feedUrl1 = "https://www.androidauthority.com/feed/"
        ObjectGraph.getFeedRepository().insertFeed(feedUrl1)

        val feedUrl2 = "http://feeds.dzone.com/java"
        ObjectGraph.getFeedRepository().insertFeed(feedUrl2)

        val feedUrl3 = "http://feeds.bbci.co.uk/news/rss.xml"
        ObjectGraph.getFeedRepository().insertFeed(feedUrl3)

        val feedUrl4 = "https://pocketnow.com/feed"
        ObjectGraph.getFeedRepository().insertFeed(feedUrl4)

        val feedUrl5 = "http://podcasts.joerogan.net/feed"
        ObjectGraph.getFeedRepository().insertFeed(feedUrl5)

        val feedUrl6 = "https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml"
        ObjectGraph.getFeedRepository().insertFeed(feedUrl6)

        val feedUrl7 = "https://www.npr.org/rss/podcast.php?id=510298"
        ObjectGraph.getFeedRepository().insertFeed(feedUrl7)
    }
}