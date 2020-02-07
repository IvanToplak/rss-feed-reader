package agency.five.cu_it_rssfeedproject.ui.feeditemdetails


import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.show
import agency.five.cu_it_rssfeedproject.ui.common.BaseFragment
import agency.five.cu_it_rssfeedproject.ui.common.ScreenTitleProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_feed_item_details.*
import org.koin.android.ext.android.inject

private const val FEED_ITEM_URL_KEY = "feedItemUrl"

class FeedItemDetailsFragment : BaseFragment(), FeedItemDetailsWebViewClient.LoadingStateListener {

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

    override fun doOnCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            feedItemUrl = it.getString(FEED_ITEM_URL_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_feed_item_details, container, false)

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        screenTitleProvider.setTitleVisibility(false)
        if (!feedItemUrl.isNullOrEmpty()) {
            setupWebView(feedItemUrl!!)
        }
    }

    override fun onDestroyView() {
        screenTitleProvider.setTitleVisibility(true)
        super.onDestroyView()
    }

    private fun setupWebView(feedItemUrl: String) {
        feed_item_details_web_view.apply {
            webViewClient = FeedItemDetailsWebViewClient(this@FeedItemDetailsFragment)
            settings.javaScriptEnabled = true
            loadUrl(feedItemUrl)
        }
    }

    override fun setLoadingState(isLoading: Boolean) {
        page_loading_progress_bar?.show(isLoading)
    }
}
