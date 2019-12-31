package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.inflate
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_feed_card.view.*

class FeedsAdapter(
    private val feeds: MutableList<FeedViewModel>,
    private val listItemOnLongClickListener: ListItemOnLongClickListener
) :
    RecyclerView.Adapter<FeedsAdapter.ViewHolder>() {

    interface ListItemOnLongClickListener {
        fun onFeedSelected(selectedFeed: FeedViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.list_item_feed_card))

    override fun getItemCount(): Int = feeds.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feeds[position])
    }

    fun updateFeeds(feeds: List<FeedViewModel>) {
        this.feeds.clear()
        this.feeds.addAll(feeds)
        notifyDataSetChanged()
    }

    fun clearSelection() {
        for (feed in feeds) {
            if (feed.isSelected) {
                feed.isSelected = false
                notifyItemChanged(feeds.indexOf(feed))
            }
        }
    }

    fun selectFeed(selectedFeed: FeedViewModel) {
        val position = feeds.indexOf(selectedFeed)
        feeds[position].isSelected = true
        notifyItemChanged(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var feed: FeedViewModel

        fun bind(feed: FeedViewModel) {
            this.feed = feed

            //load feed image from image url or use default if blank
            if (feed.imageUrl.isEmpty()) {
                itemView.feed_image.setImageResource(R.drawable.baseline_rss_feed_black_48)
            } else {
                Picasso.get()
                    .load(feed.imageUrl)
                    .placeholder(R.drawable.baseline_rss_feed_black_48)
                    .error(R.drawable.baseline_rss_feed_black_48)
                    .into(itemView.feed_image)
            }

            itemView.feed_title_text_view.text = feed.title
            itemView.feed_description_text_view.text = feed.description
            itemView.setOnLongClickListener {
                listItemOnLongClickListener.onFeedSelected(feed)
                false
            }
            val backgroundColor = if (feed.isSelected) ContextCompat.getColor(
                itemView.context,
                R.color.selectedFeed
            ) else Color.TRANSPARENT
            itemView.list_item_feed_container.setBackgroundColor(backgroundColor)
        }
    }
}