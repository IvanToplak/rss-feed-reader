package agency.five.cu_it_rssfeedproject.data.service.parser

import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeed
import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeedItem
import com.einmalfel.earl.EarlParser
import java.net.URL


class EarlFeedParserWrapper : FeedParserWrapper {

    override fun parseFeed(feedUrl: String): ApiFeed {
        val inputStream = URL(feedUrl).openConnection().getInputStream()
        inputStream.use {
            val feed = EarlParser.parseOrThrow(inputStream, 0)
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
}