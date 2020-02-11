package agency.five.cu_it_rssfeedproject.data.service.parser

import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeed
import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeedItem
import android.util.Log
import com.einmalfel.earl.EarlParser
import com.einmalfel.earl.Feed
import io.reactivex.Single
import java.net.URL

const val EARL_FEED_PARSER_TAG = "EarlFeedParser"
const val ERROR_MESSAGE = "Error retrieving feed"

class EarlFeedParserWrapper : FeedParser {

    override fun parseFeed(feedUrl: String) = Single.create<ApiFeed> { emitter ->
        try {
            val inputStream = URL(feedUrl).openConnection().getInputStream()
            val feed = EarlParser.parseOrThrow(inputStream, 0)
            emitter.onSuccess(mapFeedToApiFeed(feed, feedUrl))
        } catch (e: Exception) {
            Log.e(EARL_FEED_PARSER_TAG, "$ERROR_MESSAGE: $feedUrl", e)
            emitter.onError(e)
        }
    }

    private fun mapFeedToApiFeed(
        feed: Feed,
        feedUrl: String
    ): ApiFeed {
        val apiFeedItems = feed.items.map { item ->
            ApiFeedItem(
                item.title ?: "",
                item.publicationDate,
                item.link ?: ""
            )
        }
        return ApiFeed(
            feed.title,
            feed.description ?: "",
            feedUrl,
            feed.imageLink ?: "",
            apiFeedItems
        )
    }
}