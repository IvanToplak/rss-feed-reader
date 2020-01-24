package agency.five.cu_it_rssfeedproject.ui.feeditemdetails

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class FeedItemDetailsWebViewClient(private val loadingStateListener: LoadingStateListener) :
    WebViewClient() {

    private var isInitialLoading = true

    interface LoadingStateListener {
        fun setLoadingState(isLoading: Boolean)
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        view.loadUrl(request.url.toString())
        return true
    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        if (isInitialLoading) {
            loadingStateListener.setLoadingState(true)
        }
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView, url: String) {
        if (isInitialLoading) {
            loadingStateListener.setLoadingState(false)
            isInitialLoading = false
        }
        super.onPageFinished(view, url)
    }
}