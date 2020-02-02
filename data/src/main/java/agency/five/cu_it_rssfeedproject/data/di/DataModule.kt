package agency.five.cu_it_rssfeedproject.data.di

import agency.five.cu_it_rssfeedproject.data.db.database.FeedDatabase
import agency.five.cu_it_rssfeedproject.data.repository.FeedRepositoryImpl
import agency.five.cu_it_rssfeedproject.data.service.FeedService
import agency.five.cu_it_rssfeedproject.data.service.FeedServiceImpl
import agency.five.cu_it_rssfeedproject.data.service.parser.*
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            FeedDatabase::class.java,
            FeedDatabase.NAME
        ).build()
    }

    single { get<FeedDatabase>().feedDao() }

    single<FeedParser>(named(EARL_FEED_PARSER_TAG)) { EarlFeedParserWrapper() }

    single<FeedParser>(named(FEED_PARSER_TAG)) { FeedParserImpl(get(named(EARL_FEED_PARSER_TAG))) }

    single<FeedService> { FeedServiceImpl(get(named(FEED_PARSER_TAG))) }

    single<FeedRepository> { FeedRepositoryImpl(get(), get()) }
}