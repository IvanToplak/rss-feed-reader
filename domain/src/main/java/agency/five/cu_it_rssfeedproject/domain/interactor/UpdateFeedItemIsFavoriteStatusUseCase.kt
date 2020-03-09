package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository

class UpdateFeedItemIsFavoriteStatusUseCase(private val feedRepository: FeedRepository) {

    fun execute(feedItemId: Int, isFavorite: Boolean) =
        feedRepository.updateFeedItemIsFavoriteStatus(feedItemId, isFavorite)
}