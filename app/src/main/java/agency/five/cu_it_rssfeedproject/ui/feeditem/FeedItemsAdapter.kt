package agency.five.cu_it_rssfeedproject.ui.feeditem

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.inflate
import agency.five.cu_it_rssfeedproject.app.show
import agency.five.cu_it_rssfeedproject.app.toString
import agency.five.cu_it_rssfeedproject.ui.model.FeedItemViewModel
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_feed_item_card.view.*

private const val DATE_PATTERN = "MMM d"

class FeedItemsAdapter(
    private val feedItems: MutableList<FeedItemViewModel>,
    private val listItemOnClickListener: ListItemOnClickListener
) : RecyclerView.Adapter<FeedItemsAdapter.ViewHolder>() {

    interface ListItemOnClickListener {
        fun onFeedItemClicked(clickedFeedItem: FeedItemViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.list_item_feed_item_card))

    override fun getItemCount(): Int = feedItems.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feedItems[position])
    }

    fun updateFeedItems(feedItems: List<FeedItemViewModel>) {
        this.feedItems.clear()
        this.feedItems.addAll(feedItems)
        notifyDataSetChanged()
    }

    fun toggleIsNewStatus(clickedFeedItem: FeedItemViewModel) {
        val position = feedItems.indexOf(clickedFeedItem)
        if (position == -1) return
        feedItems[position].isNew = !feedItems[position].isNew
        notifyItemChanged(position)
    }

    fun allItemsRead() = feedItems.all { item -> !item.isNew }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var feedItem: FeedItemViewModel

        fun bind(feedItem: FeedItemViewModel) {
            this.feedItem = feedItem

            itemView.feed_item_title_text_view.text = feedItem.title
            itemView.feed_item_date_text_view.text =
                feedItem.publicationDate?.toString(DATE_PATTERN)
            itemView.feed_item_new_item_indicator_text_view.show(feedItem.isNew)
            itemView.setOnClickListener {
                listItemOnClickListener.onFeedItemClicked(feedItem)
            }
        }
    }
}