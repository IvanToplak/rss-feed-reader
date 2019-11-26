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
    }
}