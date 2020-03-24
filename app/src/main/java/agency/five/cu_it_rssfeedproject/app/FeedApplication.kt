package agency.five.cu_it_rssfeedproject.app

import agency.five.cu_it_rssfeedproject.data.di.dataModule
import agency.five.cu_it_rssfeedproject.di.appModule
import agency.five.cu_it_rssfeedproject.device.di.deviceModule
import agency.five.cu_it_rssfeedproject.domain.di.domainModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FeedApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FeedApplication)
            androidLogger()
            modules(listOf(appModule, domainModule, dataModule, deviceModule))
        }
    }
}