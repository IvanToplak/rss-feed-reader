package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository

class AddNewFeedUseCase(private val feedRepository: FeedRepository) {

    fun execute(feedUrl: String, callback: FeedRepository.NewFeedResultCallback) {
        feedRepository.insertFeed(feedUrl, callback)
    }
}