package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.model.FeedItem
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import io.reactivex.Single

class GetFeedItemsUseCase(private val feedRepository: FeedRepository) {

    fun execute(feedId: Int): Single<List<FeedItem>> {
        return feedRepository.getFeedItems(feedId)
    }
}