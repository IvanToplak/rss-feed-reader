package agency.five.cu_it_rssfeedproject.data.service.parser

import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeed

interface FeedParserWrapper {

    fun parseFeed(feedUrl: String): ApiFeed
}