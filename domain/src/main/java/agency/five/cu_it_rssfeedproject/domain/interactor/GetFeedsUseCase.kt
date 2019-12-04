package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository

class GetFeedsUseCase(private val feedRepository: FeedRepository) {

    fun execute(callback: FeedRepository.FeedsResultCallback) {
        feedRepository.getFeeds(callback)
    }
}