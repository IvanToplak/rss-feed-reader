package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository

class GetNewFeedItemsNotificationPrefUseCase(private val feedRepository: FeedRepository) {

    fun execute() = feedRepository.getNewFeedItemsNotificationPref()
}