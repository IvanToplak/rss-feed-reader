package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewData
import io.reactivex.Flowable

interface FeedItemsContract {

    interface View

    interface ViewModel {
        fun getFeedItems(feedId: Int): Flowable<List<FeedItemViewData>>
        fun updateFeedItemIsNewStatus(feedItemViewData: FeedItemViewData, isNew: Boolean)
        fun updateFeedItemIsFavoriteStatus(
            feedItemViewData: FeedItemViewData,
            isFavorite: Boolean
        )

        fun getFavoriteFeedItems(): Flowable<List<FeedItemViewData>>
    }
}