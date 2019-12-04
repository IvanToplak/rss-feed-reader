package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.inflate
import agency.five.cu_it_rssfeedproject.ui.model.FeedViewModel
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_feed_card.view.*

class FeedsAdapter(private val feeds: MutableList<FeedViewModel>) :
    RecyclerView.Adapter<FeedsAdapter.ViewHolder>() {

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
        }
    }
}