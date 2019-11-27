package agency.five.cu_it_rssfeedproject.domain.repository

import agency.five.cu_it_rssfeedproject.domain.model.Feed

interface FeedRepository {

    interface FeedsResultCallback {
        fun onGetFeedsResponse(feeds: List<Feed>)
    }

    fun insertFeed(feedUrl: String)

    fun getFeeds(callback: FeedsResultCallback)
}