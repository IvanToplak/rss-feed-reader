package agency.five.cu_it_rssfeedproject.data.service.parser

import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeed
import io.reactivex.Single

const val FEED_PARSER_TAG = "FeedParser"

class FeedParserImpl(private val parserWrapper: FeedParser) : FeedParser {

    override fun parseFeed(feedUrl: String): Single<ApiFeed> {
        return parserWrapper.parseFeed(feedUrl)
    }
}