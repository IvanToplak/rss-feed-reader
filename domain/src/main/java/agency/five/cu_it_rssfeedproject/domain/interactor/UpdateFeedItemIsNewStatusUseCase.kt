package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository

class UpdateFeedItemIsNewStatusUseCase(private val feedRepository: FeedRepository) {

    fun execute(feedItemId: Int, isNew: Boolean) =
        feedRepository.updateFeedItemIsNewStatus(feedItemId, isNew)
}