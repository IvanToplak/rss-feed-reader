package agency.five.cu_it_rssfeedproject.ui.feeditemdetails


import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.show
import agency.five.cu_it_rssfeedproject.ui.common.ScreenTitleProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_feed_item_details.*
import org.koin.android.ext.android.inject

private const val FEED_ITEM_URL_KEY = "feedItemUrl"

class FeedItemDetailsFragment : Fragment(), FeedItemDetailsWebViewClient.LoadingStateListener {

    private val screenTitleProvider: ScreenTitleProvider by inject()

    private var feedItemUrl: String? = null

    companion object {
        const val TAG = "feedItemDetails"
        fun newInstance(feedItemUrl: String) =
            FeedItemDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(FEED_ITEM_URL_KEY, feedItemUrl)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            feedItemUrl = it.getString(FEED_ITEM_URL_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed_item_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        screenTitleProvider.setTitleVisibility(false)
        if (!feedItemUrl.isNullOrEmpty()) {
            setupWebView(feedItemUrl!!)
        }
    }

    private fun setupWebView(feedItemUrl: String) {
        feed_item_details_web_view.apply {
            webViewClient = FeedItemDetailsWebViewClient(this@FeedItemDetailsFragment)
            settings.javaScriptEnabled = true
            loadUrl(feedItemUrl)
        }
    }

    override fun onDestroyView() {
        screenTitleProvider.setTitleVisibility(true)
        super.onDestroyView()
    }

    override fun setLoadingState(isLoading: Boolean) {
        page_loading_progress_bar?.show(isLoading)
    }
}
