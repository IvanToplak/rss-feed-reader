package agency.five.cu_it_rssfeedproject.di

import agency.five.cu_it_rssfeedproject.app.FeedApplication
import agency.five.cu_it_rssfeedproject.data.db.database.FeedDatabase
import agency.five.cu_it_rssfeedproject.data.repository.FeedRepositoryImpl
import agency.five.cu_it_rssfeedproject.data.service.FeedServiceImpl
import agency.five.cu_it_rssfeedproject.data.service.parser.EarlFeedParserWrapper
import agency.five.cu_it_rssfeedproject.data.service.parser.FeedParserImpl
import agency.five.cu_it_rssfeedproject.domain.interactor.*
import agency.five.cu_it_rssfeedproject.ui.common.ScreenTitleProvider
import agency.five.cu_it_rssfeedproject.ui.common.ScreenTitleProviderImpl
import agency.five.cu_it_rssfeedproject.ui.feed.FeedsContract
import agency.five.cu_it_rssfeedproject.ui.feed.FeedsPresenter
import agency.five.cu_it_rssfeedproject.ui.feed.NewFeedContract
import agency.five.cu_it_rssfeedproject.ui.feed.NewFeedPresenter
import agency.five.cu_it_rssfeedproject.ui.feeditem.FeedItemsContract
import agency.five.cu_it_rssfeedproject.ui.feeditem.FeedItemsPresenter
import agency.five.cu_it_rssfeedproject.ui.router.Router
import agency.five.cu_it_rssfeedproject.ui.router.RouterImpl
import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.room.Room

object ObjectGraph {

    //create Scope enum if there is more than one scope
    const val mainActivityScope = "mainActivity"

    private var database: FeedDatabase? = null

    private var scopedRouter: MutableMap<String, Router> = mutableMapOf()

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

    fun setScopedRouter(scope: String, fragmentManager: FragmentManager) {
        //use when expression if there is more than one activity (scope)
        scopedRouter[scope] = RouterImpl(fragmentManager)
    }

    fun getScopedRouter(scope: String): Router? {
        return scopedRouter[scope]
    }

    fun removeScopedRouter(scope: String) {
        scopedRouter.remove(scope)
    }

    private fun getFeedParserWrapper() = EarlFeedParserWrapper()

    private fun getFeedParser() = FeedParserImpl(getFeedParserWrapper())

    private fun getFeedService() =
        FeedServiceImpl(getFeedParser())

    private fun getFeedDao() = getDatabase(FeedApplication.getAppContext()).feedDao()

    fun getFeedRepository() = FeedRepositoryImpl(getFeedDao(), getFeedService())

    private fun getGetFeedsUseCase() = GetFeedsUseCase(getFeedRepository())

    private fun getAddNewFeedUseCase() = AddNewFeedUseCase(getFeedRepository())

    private fun getDeleteFeedUseCase() = DeleteFeedUseCase(getFeedRepository())

    private fun getFeedItemsUseCase() = GetFeedItemsUseCase(getFeedRepository())

    private fun getAddFeedItemsToFeedUseCase() = AddFeedItemsToFeedUseCase(getFeedRepository())

    fun getFeedsPresenter(view: FeedsContract.View) =
        FeedsPresenter(
            view,
            getGetFeedsUseCase(),
            getDeleteFeedUseCase(),
            getAddFeedItemsToFeedUseCase()
        )

    fun getNewFeedPresenter(view: NewFeedContract.View) =
        NewFeedPresenter(view, getAddNewFeedUseCase())

    fun getFeedItemsPresenter(view: FeedItemsContract.View) =
        FeedItemsPresenter(view, getFeedItemsUseCase())

    fun getScreenTitleProvider(): ScreenTitleProvider = ScreenTitleProviderImpl
}