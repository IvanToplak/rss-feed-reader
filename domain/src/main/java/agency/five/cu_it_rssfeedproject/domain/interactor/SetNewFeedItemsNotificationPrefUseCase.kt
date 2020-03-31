package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository

class SetNewFeedItemsNotificationPrefUseCase(private val feedRepository: FeedRepository) {

    fun execute(newFeedItemsNotificationEnabled: Boolean) =
        feedRepository.setNewFeedItemsNotificationPref(newFeedItemsNotificationEnabled)
}