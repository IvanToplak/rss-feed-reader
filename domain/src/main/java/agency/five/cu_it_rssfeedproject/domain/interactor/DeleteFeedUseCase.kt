package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import io.reactivex.Completable

class DeleteFeedUseCase(private val feedRepository: FeedRepository) {

    fun execute(feed: Feed): Completable {
        return feedRepository.deleteFeed(feed)
    }
}