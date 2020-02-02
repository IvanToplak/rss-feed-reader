package agency.five.cu_it_rssfeedproject.data.service.parser

import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeed
import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeedItem
import com.einmalfel.earl.EarlParser
import com.einmalfel.earl.Feed
import java.net.URL

const val EARL_FEED_PARSER_TAG = "EarlFeedParser"

class EarlFeedParserWrapper : FeedParser {

    override fun parseFeed(feedUrl: String): ApiFeed {
        val inputStream = URL(feedUrl).openConnection().getInputStream()

        val feed = EarlParser.parseOrThrow(inputStream, 0)
        return mapFeedToApiFeed(feed, feedUrl)
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