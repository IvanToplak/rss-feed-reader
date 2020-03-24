package agency.five.cu_it_rssfeedproject.device.di

import agency.five.cu_it_rssfeedproject.device.notification.Notifications
import agency.five.cu_it_rssfeedproject.device.notification.NotificationsImpl
import agency.five.cu_it_rssfeedproject.device.work.Work
import agency.five.cu_it_rssfeedproject.device.work.WorkImpl
import androidx.core.app.NotificationManagerCompat
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val deviceModule = module {

    single<Work> { WorkImpl(get()) }

    single { NotificationManagerCompat.from(androidContext()) }

    single<Notifications> { NotificationsImpl(get()) }
}