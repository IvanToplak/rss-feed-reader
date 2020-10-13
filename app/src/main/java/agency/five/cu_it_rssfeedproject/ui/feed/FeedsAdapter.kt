package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.inflate
import agency.five.cu_it_rssfeedproject.app.show
import agency.five.cu_it_rssfeedproject.ui.feeditem.FEED_ITEM_TO_DETAILS_TRANSITION_NAME
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewData
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_feed_card.view.*

const val FEED_TO_FEED_ITEMS_TRANSITION_NAME = "feed_to_feed_items"

class FeedsAdapter(
    private val feeds: MutableList<FeedViewData>,
    private val listItemOnLongClickListener: ListItemOnLongClickListener,
    private val listItemOnClickListener: ListItemOnClickListener
) :
    RecyclerView.Adapter<FeedsAdapter.ViewHolder>() {

    interface ListItemOnLongClickListener {
        fun onFeedSelected(selectedFeed: FeedViewData)
    }

    interface ListItemOnClickListener {
        fun onFeedClicked(clickedFeed: FeedViewData, clickedView: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.list_item_feed_card))

    override fun getItemCount(): Int = feeds.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feeds[position])
    }

    fun updateFeeds(feeds: List<FeedViewData>) {
        this.feeds.clear()
        this.feeds.addAll(feeds)
        notifyDataSetChanged()
    }

    fun toggleSelection(selectedFeed: FeedViewData) {
        val position = feeds.indexOf(selectedFeed)
        if (position == -1) return
        feeds[position].isSelected = !feeds[position].isSelected
        notifyItemChanged(position)
    }

    fun selectFeed(feedId: Int): FeedViewData? {
        val feed = feeds.firstOrNull { it.id == feedId }
        return if (feed != null) {
            if (!feed.isSelected) {
                toggleSelection(feed)
            }
            feed
        } else null
    }

    fun clearSelection() {
        for (feed in feeds) {
            feed.isSelected = false
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var feed: FeedViewData

        fun bind(feed: FeedViewData) {
            this.feed = feed

            //load feed image from image url or use default if blank
            if (feed.imageUrl.isEmpty()) {
                itemView.feed_image.setImageResource(R.drawable.ic_rss_feed_48)
            } else {
                Picasso.get()
                    .load(feed.imageUrl)
                    .placeholder(R.drawable.ic_rss_feed_48)
                    .error(R.drawable.ic_rss_feed_48)
                    .into(itemView.feed_image)
            }

            itemView.feed_title_text_view.text = feed.title
            itemView.feed_description_text_view.text = feed.description
            val card = itemView.list_item_feed_card
            card.setOnLongClickListener {
                listItemOnLongClickListener.onFeedSelected(feed)
                false
            }
            card.setOnClickListener {
                listItemOnClickListener.onFeedClicked(feed, card)
            }
            card.isChecked = feed.isSelected
            card.transitionName = "${FEED_ITEM_TO_DETAILS_TRANSITION_NAME}_${feed.id}"
            itemView.feed_new_feed_items_indicator_text_view.show(feed.hasUnreadItems)
        }
    }
}