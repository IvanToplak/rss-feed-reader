package agency.five.cu_it_rssfeedproject.data.service

import agency.five.cu_it_rssfeedproject.data.service.model.ApiFeed
import io.reactivex.Single

interface FeedService {

    fun getFeed(feedUrl: String): Single<ApiFeed>
}