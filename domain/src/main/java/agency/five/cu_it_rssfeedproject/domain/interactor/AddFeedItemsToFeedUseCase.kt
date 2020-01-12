package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository

class AddFeedItemsToFeedUseCase(private val feedRepository: FeedRepository) {

    fun execute(feed: Feed) {
        feedRepository.addFeedItemsToFeed(feed)
    }
}