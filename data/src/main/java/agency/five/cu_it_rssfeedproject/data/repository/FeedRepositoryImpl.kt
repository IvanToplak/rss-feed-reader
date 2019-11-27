package agency.five.cu_it_rssfeedproject.data.repository

import agency.five.cu_it_rssfeedproject.data.db.dao.FeedDao
import agency.five.cu_it_rssfeedproject.data.mappings.mapApiFeedToDbFeed
import agency.five.cu_it_rssfeedproject.data.mappings.mapDbFeedToFeed
import agency.five.cu_it_rssfeedproject.data.service.FeedService
import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import android.os.AsyncTask
import android.util.Log

private const val TAG = "FEED_REPOSITORY"
private const val INSERT_ERROR_MESSAGE = "Error inserting feed"

class FeedRepositoryImpl(private val feedDao: FeedDao, private val feedService: FeedService) :
    FeedRepository {

    override fun insertFeed(feedUrl: String) {
        InsertFeedAsyncTask(feedDao, feedService).execute(feedUrl)
    }

    override fun getFeeds(callback: FeedRepository.FeedsResultCallback) {
        GetFeedsAsyncTask(feedDao, callback).execute()
    }

    private class InsertFeedAsyncTask(
        private val feedDao: FeedDao,
        private val feedService: FeedService
    ) :
        AsyncTask<String, Void, Void>() {
        override fun doInBackground(vararg params: String): Void? {
            val apiFeed = feedService.getFeed(params[0])
            //fail gracefully for now
            if (apiFeed.title.isBlank()) return null
            val bdFeed = mapApiFeedToDbFeed(apiFeed)
            try {
                feedDao.insert(bdFeed)
            } catch (e: Exception) {
                Log.e(TAG, "$INSERT_ERROR_MESSAGE: $params[0]", e)
            }
            return null
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
}