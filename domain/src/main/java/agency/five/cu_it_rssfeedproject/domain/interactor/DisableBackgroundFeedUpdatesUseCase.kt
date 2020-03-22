package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.background.FeedsUpdateScheduler

class DisableBackgroundFeedUpdatesUseCase(private val feedsUpdateScheduler: FeedsUpdateScheduler) {

    fun execute() = feedsUpdateScheduler.cancelFeedsBackgroundUpdates()
}