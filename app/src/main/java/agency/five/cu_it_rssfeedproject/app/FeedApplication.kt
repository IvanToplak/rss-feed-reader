package agency.five.cu_it_rssfeedproject.app

import agency.five.cu_it_rssfeedproject.data.di.dataModule
import agency.five.cu_it_rssfeedproject.di.appModule
import agency.five.cu_it_rssfeedproject.domain.di.domainModule
import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

const val ALL_FEED_ITEMS_READ = "ALL_FEED_ITEMS_READ"

class FeedApplication : Application() {

    companion object {
        private lateinit var instance: FeedApplication

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()

        startKoin {
            androidContext(this@FeedApplication)
            androidLogger()
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}