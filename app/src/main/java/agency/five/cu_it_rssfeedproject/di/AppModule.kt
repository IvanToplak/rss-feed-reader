package agency.five.cu_it_rssfeedproject.di

import agency.five.cu_it_rssfeedproject.ui.common.ScreenTitleProvider
import agency.five.cu_it_rssfeedproject.ui.common.ScreenTitleProviderImpl
import agency.five.cu_it_rssfeedproject.ui.feed.*
import agency.five.cu_it_rssfeedproject.ui.feeditem.FeedItemsContract
import agency.five.cu_it_rssfeedproject.ui.feeditem.FeedItemsFragment
import agency.five.cu_it_rssfeedproject.ui.feeditem.FeedItemsPresenter
import agency.five.cu_it_rssfeedproject.ui.router.Router
import agency.five.cu_it_rssfeedproject.ui.router.RouterImpl
import androidx.fragment.app.FragmentManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_ACTIVITY_SCOPE = "mainActivity"
const val MAIN_ACTIVITY_SCOPE_ID = "mainActivityScopeId"
const val MAIN_THREAD = "mainThread"
const val BACKGROUND_THREAD = "backgroundThread"

val appModule = module {

    single {
        mapOf(
            MAIN_THREAD to AndroidSchedulers.mainThread(),
            BACKGROUND_THREAD to Schedulers.io()
        )
    }

    single<ScreenTitleProvider> { ScreenTitleProviderImpl() }

    scope(named(MAIN_ACTIVITY_SCOPE)) {
        scoped<Router> { (fragmentManager: FragmentManager) -> RouterImpl(fragmentManager) }
    }

    scope(named<FeedsFragment>()) {
        scoped<FeedsContract.Presenter> {
            FeedsPresenter(
                getScope(MAIN_ACTIVITY_SCOPE_ID).get(),
                get(),
                get(),
                get(),
                get()
            )
        }
    }

    scope(named<NewFeedFragment>()) {
        scoped<NewFeedContract.Presenter> {
            NewFeedPresenter(
                getScope(MAIN_ACTIVITY_SCOPE_ID).get(),
                get(),
                get()
            )
        }
    }

    scope(named<FeedItemsFragment>()) {
        scoped<FeedItemsContract.Presenter> {
            FeedItemsPresenter(
                getScope(MAIN_ACTIVITY_SCOPE_ID).get(),
                get(),
                get()
            )
        }
    }
}