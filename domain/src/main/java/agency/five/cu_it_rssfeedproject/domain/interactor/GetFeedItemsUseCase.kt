package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository

class GetFeedItemsUseCase(private val feedRepository: FeedRepository) {

    fun execute(feedId: Int, callback: FeedRepository.FeedItemsResultCallback) {
        feedRepository.getFeedItems(feedId, callback)
    }
}