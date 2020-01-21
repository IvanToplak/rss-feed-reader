package agency.five.cu_it_rssfeedproject.domain.repository

import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.model.FeedItem

interface FeedRepository {

    interface FeedsResultCallback {
        fun onGetFeedsResponse(feeds: List<Feed>)
    }

    interface FeedItemsResultCallback {
        fun onGetFeedItemsResponse(feedItems: List<FeedItem>)
    }

    interface NewFeedResultCallback {
        fun onInsertFeedResponse(success: Boolean)
    }

    interface DeleteFeedResultCallback {
        fun onDeleteFeedResponse(success: Boolean)
    }

    fun insertFeed(feedUrl: String, callback: NewFeedResultCallback)

    fun getFeeds(callback: FeedsResultCallback)

    fun deleteFeed(feed: Feed, callback: DeleteFeedResultCallback)

    fun getFeedItems(feedId: Int, callback: FeedItemsResultCallback)

    fun addFeedItemsToFeed(feed: Feed)
}