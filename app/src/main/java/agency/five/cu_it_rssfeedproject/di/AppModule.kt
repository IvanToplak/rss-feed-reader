package agency.five.cu_it_rssfeedproject.di

import agency.five.cu_it_rssfeedproject.MainActivity
import agency.five.cu_it_rssfeedproject.domain.background.FeedsUpdateScheduler
import agency.five.cu_it_rssfeedproject.ui.background.FeedsUpdateSchedulerImpl
import agency.five.cu_it_rssfeedproject.ui.background.FeedsUpdateWorkRequestFactory
import agency.five.cu_it_rssfeedproject.ui.common.AppSchedulers
import agency.five.cu_it_rssfeedproject.ui.common.ScreenTitleProvider
import agency.five.cu_it_rssfeedproject.ui.common.ScreenTitleProviderImpl
import agency.five.cu_it_rssfeedproject.ui.feed.FeedsViewModel
import agency.five.cu_it_rssfeedproject.ui.feed.NewFeedViewModel
import agency.five.cu_it_rssfeedproject.ui.feeditem.FeedItemsViewModel
import agency.five.cu_it_rssfeedproject.ui.notification.NotificationFactory
import agency.five.cu_it_rssfeedproject.ui.notification.NotificationFactoryImpl
import agency.five.cu_it_rssfeedproject.ui.router.Router
import agency.five.cu_it_rssfeedproject.ui.router.RouterImpl
import agency.five.cu_it_rssfeedproject.ui.router.RouterProvider
import agency.five.cu_it_rssfeedproject.ui.router.RouterProviderImpl
import android.app.PendingIntent
import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.work.WorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

const val MAIN_ACTIVITY_SCOPE = "mainActivity"
const val MAIN_ACTIVITY_SCOPE_ID = "mainActivityScopeId"
private const val FEEDS_UPDATE_REPEAT_INTERVAL = 15L

val appModule = module {

    single {
        AppSchedulers()
    }

    single<ScreenTitleProvider> { ScreenTitleProviderImpl() }

    scope(named(MAIN_ACTIVITY_SCOPE)) {
        scoped<Router> { (fragmentManager: FragmentManager) -> RouterImpl(fragmentManager) }
    }

    single<RouterProvider> { RouterProviderImpl() }

    viewModel {
        FeedsViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel {
        NewFeedViewModel(
            get(),
            get(),
            get()
        )
    }

    viewModel {
        FeedItemsViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    single { WorkManager.getInstance(androidContext()) }

    single {
        FeedsUpdateWorkRequestFactory.createWorkRequest(
            FEEDS_UPDATE_REPEAT_INTERVAL,
            TimeUnit.MINUTES
        )
    }

    single<FeedsUpdateScheduler> { FeedsUpdateSchedulerImpl(get(), get()) }

    single<NotificationFactory> { NotificationFactoryImpl(androidContext(), get()) }

    /**
     * New feed items notification PendingIntent - start MainActivity on notification tap
     */
    single {
        val mainActivityIntent = Intent(androidContext(), MainActivity::class.java)
        mainActivityIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        PendingIntent.getActivity(
            androidContext(),
            0,
            mainActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}