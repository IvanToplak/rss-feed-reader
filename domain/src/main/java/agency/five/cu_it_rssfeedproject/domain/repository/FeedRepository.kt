package agency.five.cu_it_rssfeedproject.domain.repository

import agency.five.cu_it_rssfeedproject.domain.model.Feed

interface FeedRepository {

    fun insertFeed(feedUrl: String)

    fun getFeeds(): List<Feed>
}