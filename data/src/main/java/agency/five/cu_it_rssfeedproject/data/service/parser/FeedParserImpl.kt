package agency.five.cu_it_rssfeedproject.data.service.parser

const val FEED_PARSER_TAG = "FeedParser"

class FeedParserImpl(private val parserWrapper: FeedParser) : FeedParser {

    override fun parseFeed(feedUrl: String) = parserWrapper.parseFeed(feedUrl)
}