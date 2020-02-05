package agency.five.cu_it_rssfeedproject.data.service

import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeed
import agency.five.cu_it_rssfeedproject.data.service.parser.FeedParser
import io.reactivex.Single

class FeedServiceImpl(private val feedParser: FeedParser) : FeedService {

    override fun getFeed(feedUrl: String): Single<ApiFeed> {
        return feedParser.parseFeed(feedUrl)
    }
}