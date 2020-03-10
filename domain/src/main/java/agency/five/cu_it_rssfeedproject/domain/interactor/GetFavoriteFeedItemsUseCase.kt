package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository

class GetFavoriteFeedItemsUseCase(private val feedRepository: FeedRepository) {

    fun execute() = feedRepository.getFavoriteFeedItems()
}