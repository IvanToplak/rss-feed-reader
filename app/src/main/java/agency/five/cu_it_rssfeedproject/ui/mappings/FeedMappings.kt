package agency.five.cu_it_rssfeedproject.ui.mappings

import agency.five.cu_it_rssfeedproject.domain.model.Feed
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel

fun mapFeedToFeedViewModel(feed: Feed, hasUnreadItems: Boolean) = FeedViewModel(
    feed.id,
    feed.title,
    feed.description,
    feed.url,
    feed.imageUrl,
    hasUnreadItems = hasUnreadItems
)

fun mapFeedViewModelToFeed(feedViewModel: FeedViewModel) = Feed(
    feedViewModel.id,
    feedViewModel.title,
    feedViewModel.description,
    feedViewModel.url,
    feedViewModel.imageUrl
)