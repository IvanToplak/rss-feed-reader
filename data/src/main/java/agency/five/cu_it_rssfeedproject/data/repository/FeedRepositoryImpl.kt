package agency.five.cu_it_rssfeedproject.data.repository

import agency.five.cu_it_rssfeedproject.data.db.dao.FeedDao
import agency.five.cu_it_rssfeedproject.data.mappings.mapApiFeedToDbFeed
import agency.five.cu_it_rssfeedproject.data.mappings.mapDbFeedToFeed
import agency.five.cu_it_rssfeedproject.data.mappings.mapFeedToDbFeed
import agency.five.cu_it_rssfeedproject.data.service.FeedService
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import android.os.AsyncTask
import android.util.Log

private const val TAG = "FEED_REPOSITORY"
private const val INSERT_ERROR_MESSAGE = "Error inserting feed"
private const val DELETE_ERROR_MESSAGE = "Error deleting feed"

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
}