package agency.five.cu_it_rssfeedproject.data.service

import agency.five.cu_it_rssfeedproject.data.service.parser.FeedParser

class FeedServiceImpl(private val feedParser: FeedParser) : FeedService {

    override fun getFeed(feedUrl: String) = feedParser.parseFeed(feedUrl)
}