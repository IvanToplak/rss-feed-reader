package agency.five.cu_it_rssfeedproject.data.repository

import agency.five.cu_it_rssfeedproject.data.db.dao.FeedDao
import agency.five.cu_it_rssfeedproject.data.mappings.*
import agency.five.cu_it_rssfeedproject.data.service.FeedService
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import android.os.AsyncTask
import android.os.AsyncTask.THREAD_POOL_EXECUTOR
import android.util.Log

private const val TAG = "FEED_REPOSITORY"
private const val INSERT_ERROR_MESSAGE = "Error inserting feed"
private const val DELETE_ERROR_MESSAGE = "Error deleting feed"
private const val INSERT_FEED_ITEMS_ERROR_MESSAGE = "Error inserting feed items for feed"

class FeedRepositoryImpl(private val feedDao: FeedDao, private val feedService: FeedService) :
    FeedRepository {

    override fun insertFeed(feedUrl: String, callback: FeedRepository.NewFeedResultCallback) {
        InsertFeedAsyncTask(feedDao, feedService, callback).execute(feedUrl)
    }

    override fun getFeeds(callback: FeedRepository.FeedsResultCallback) {
        GetFeedsAsyncTask(feedDao, callback).execute()
    }

    override fun deleteFeed(feed: Feed, callback: FeedRepository.DeleteFeedResultCallback) {
        DeleteFeedAsyncTask(feedDao, callback).execute(feed)
    }

    override fun getFeedItems(feedId: Int, callback: FeedRepository.FeedItemsResultCallback) {
        GetFeedsItemsAsyncTask(feedDao, callback).execute(feedId)
    }

    override fun addFeedItemsToFeed(feed: Feed) {
        AddFeedItemsToFeedAsyncTask(feedDao, feedService).executeOnExecutor(THREAD_POOL_EXECUTOR, feed)
    }

    private class InsertFeedAsyncTask(
        private val feedDao: FeedDao,
        private val feedService: FeedService,
        private val callback: FeedRepository.NewFeedResultCallback
    ) :
        AsyncTask<String, Void, Boolean>() {
        override fun doInBackground(vararg params: String): Boolean {
            val apiFeed = feedService.getFeed(params[0])
            if (apiFeed.title.isBlank()) return false
            val bdFeed = mapApiFeedToDbFeed(apiFeed)
            try {
                feedDao.insert(bdFeed)
            } catch (e: Exception) {
                Log.e(TAG, "$INSERT_ERROR_MESSAGE: $params[0]", e)
            }
            return true
        }

        override fun onPostExecute(result: Boolean) {
            callback.onInsertFeedResponse(result)
        }
    }

    private class GetFeedsAsyncTask(
        private val feedDao: FeedDao,
        private val callback: FeedRepository.FeedsResultCallback
    ) :
        AsyncTask<Void, Void, List<Feed>>() {
        override fun doInBackground(vararg params: Void): List<Feed> {
            return feedDao.getFeeds().map { feed -> mapDbFeedToFeed(feed) }
        }

        override fun onPostExecute(result: List<Feed>) {
            callback.onGetFeedsResponse(result)
        }
    }

    private class DeleteFeedAsyncTask(
        private val feedDao: FeedDao,
        private val callback: FeedRepository.DeleteFeedResultCallback
    ) :
        AsyncTask<Feed, Void, Boolean>() {
        override fun doInBackground(vararg params: Feed): Boolean {
            try {
                feedDao.delete(mapFeedToDbFeed(params[0]))
            } catch (e: Exception) {
                Log.e(TAG, "$DELETE_ERROR_MESSAGE: $params[0]", e)
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean) {
            callback.onDeleteFeedResponse(result)
        }
    }

    private class GetFeedsItemsAsyncTask(
        private val feedDao: FeedDao,
        private val callback: FeedRepository.FeedItemsResultCallback
    ) :
        AsyncTask<Int, Void, List<FeedItem>>() {
        override fun doInBackground(vararg params: Int?): List<FeedItem> {
            params[0]?.let { feedId ->
                return feedDao.getFeedItems(feedId).map { feedItem -> mapDbFeedItemToFeedItem(feedItem) }
            }
            return emptyList()
        }

        override fun onPostExecute(result: List<FeedItem>) {
            callback.onGetFeedItemsResponse(result)
        }
    }

    private class AddFeedItemsToFeedAsyncTask(
        private val feedDao: FeedDao,
        private val feedService: FeedService
    ) :
        AsyncTask<Feed, Void, Unit>() {
        override fun doInBackground(vararg params: Feed) {
            val apiFeed = feedService.getFeed(params[0].url)
            if (apiFeed.title.isBlank()) return
            val bdFeedItems = apiFeed.feedItems.map { feedItem -> mapApiFeedItemToDbFeedItem(feedItem, params[0].id)}
            try {
                feedDao.insert(bdFeedItems)
            } catch (e: Exception) {
                Log.e(TAG, "$INSERT_FEED_ITEMS_ERROR_MESSAGE: $params[0]", e)
            }
        }
    }
}