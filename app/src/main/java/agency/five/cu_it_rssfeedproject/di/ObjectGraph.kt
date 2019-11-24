package agency.five.cu_it_rssfeedproject.di

import agency.five.cu_it_rssfeedproject.app.FeedApplication
import agency.five.cu_it_rssfeedproject.data.repository.FeedRepositoryImpl
import agency.five.cu_it_rssfeedproject.data.service.FeedServiceImpl
import agency.five.cu_it_rssfeedproject.data.service.parser.EarlFeedParserWrapper
import agency.five.cu_it_rssfeedproject.data.service.parser.FeedParserImpl

object ObjectGraph {

    private fun getFeedParserWrapper() = EarlFeedParserWrapper()

    private fun getFeedParser() = FeedParserImpl(getFeedParserWrapper())

    private fun getFeedService() =
        FeedServiceImpl(getFeedParser())

    private fun getFeedDao() = FeedApplication.database.feedDao()

    fun getFeedRepository() = FeedRepositoryImpl(getFeedDao(), getFeedService())
}