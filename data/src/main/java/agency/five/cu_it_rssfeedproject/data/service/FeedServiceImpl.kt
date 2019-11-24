package agency.five.cu_it_rssfeedproject.data.service

import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeed
import agency.five.cu_it_rssfeedproject.data.service.parser.FeedParser
import android.util.Log
import java.lang.Exception

class FeedServiceImpl(private val feedParser: FeedParser) : FeedService {

    companion object {
        const val TAG = "FEED_SERVICE"
        const val ERROR_MESSAGE = "Error retrieving feed"
    }

    override fun getFeed(feedUrl: String): ApiFeed {
        return try {
            feedParser.parseFeed(feedUrl)
        } catch (e: Exception) {
            Log.e(TAG, "$ERROR_MESSAGE: $feedUrl", e)
            ApiFeed()
        }
    }
}