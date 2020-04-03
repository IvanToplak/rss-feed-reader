package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel
import io.reactivex.Flowable

interface FeedItemsContract {

    interface View

    interface ViewModel {
        fun getFeedItems(feedId: Int): Flowable<List<FeedItemViewModel>>
        fun updateFeedItemIsNewStatus(feedItemViewModel: FeedItemViewModel, isNew: Boolean)
        fun updateFeedItemIsFavoriteStatus(
            feedItemViewModel: FeedItemViewModel,
            isFavorite: Boolean
        )
        fun getFavoriteFeedItems(): Flowable<List<FeedItemViewModel>>
        fun showFeedItemDetails(feedItemViewModel: FeedItemViewModel)
    }
}