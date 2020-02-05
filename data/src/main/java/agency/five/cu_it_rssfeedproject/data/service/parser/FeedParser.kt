package agency.five.cu_it_rssfeedproject.data.service.parser

import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeed
import io.reactivex.Single

interface FeedParser {

    fun parseFeed(feedUrl: String): Single<ApiFeed>
}