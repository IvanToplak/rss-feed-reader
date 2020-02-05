package agency.five.cu_it_rssfeedproject.domain.interactor

import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.domain.repository.FeedRepository
import io.reactivex.Single

class GetFeedsUseCase(private val feedRepository: FeedRepository) {

    fun execute(): Single<List<Feed>> {
        return feedRepository.getFeeds()
    }
}