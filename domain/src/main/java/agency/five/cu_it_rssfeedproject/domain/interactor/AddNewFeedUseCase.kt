package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import io.reactivex.Completable

class AddNewFeedUseCase(private val feedRepository: FeedRepository) {

    fun execute(feedUrl: String): Completable {
        return feedRepository.insertFeed(feedUrl)
    }
}