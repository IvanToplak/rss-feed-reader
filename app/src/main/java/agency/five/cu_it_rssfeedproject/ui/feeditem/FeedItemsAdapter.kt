package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.inflate
import agency.five.cu_it_rssfeedproject.app.show
import agency.five.cu_it_rssfeedproject.app.toString
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewData
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_feed_item_card.view.*

private const val DATE_PATTERN = "MMM d"
const val FEED_ITEM_TO_DETAILS_TRANSITION_NAME = "feed_item_to_details"

class FeedItemsAdapter(
    private val feedItems: MutableList<FeedItemViewData>,
    private val listItemOnClickListener: ListItemOnClickListener,
    private val favoriteButtonOnClickListener: FavoriteButtonOnClickListener
) : RecyclerView.Adapter<FeedItemsAdapter.ViewHolder>() {

    interface ListItemOnClickListener {
        fun onFeedItemClicked(clickedFeedItem: FeedItemViewData, clickedView: View)
    }

    interface FavoriteButtonOnClickListener {
        fun onFavoriteButtonClicked(clickedFeedItem: FeedItemViewData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.list_item_feed_item_card))

    override fun getItemCount(): Int = feedItems.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feedItems[position])
    }

    fun updateFeedItems(feedItems: List<FeedItemViewData>) {
        this.feedItems.clear()
        this.feedItems.addAll(feedItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var feedItem: FeedItemViewData

        fun bind(feedItem: FeedItemViewData) {
            this.feedItem = feedItem

            itemView.feed_item_title_text_view.text = feedItem.title
            itemView.feed_item_date_text_view.text =
                feedItem.publicationDate?.toString(DATE_PATTERN)
            itemView.feed_item_new_item_indicator_text_view.show(feedItem.isNew)

            itemView.list_item_feed_item_card.transitionName = "${FEED_ITEM_TO_DETAILS_TRANSITION_NAME}_${feedItem.id}"

            itemView.setOnClickListener {
                listItemOnClickListener.onFeedItemClicked(feedItem, itemView.list_item_feed_item_card)
            }

            itemView.feed_item_favorite_item_button.setOnClickListener {
                favoriteButtonOnClickListener.onFavoriteButtonClicked(feedItem)
            }

            val favoriteImageResId =
                if (feedItem.isFavorite) R.drawable.favorite_fill_24dp else R.drawable.favorite_border_empty_24dp
            itemView.feed_item_favorite_item_button.setImageResource(favoriteImageResId)
        }
    }
}