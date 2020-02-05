package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.show
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_new_feed.*
import org.koin.androidx.scope.currentScope

class NewFeedFragment : Fragment(), NewFeedContract.View {

    private val presenter: NewFeedContract.Presenter by currentScope.inject()

    companion object {
        const val TAG = "newFeed"
        fun newInstance() = NewFeedFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.onViewCreated(this)
        setupBackground()
        setupButtons()
        showLoadingState(false)
        showErrorMessage(false)
    }

    override fun onDestroyView() {
        presenter.onDestroyView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun setupBackground() {
        new_feed_container.setOnClickListener {
            presenter.back()
        }
    }

    private fun setupButtons() {
        new_feed_add_button.setOnClickListener {
            presenter.addNewFeed(new_feed_url_edit_text.text.toString())
        }
    }

    override fun showErrorMessage(isError: Boolean) {
        new_feed_error_message_text_view?.show(isError)
    }

    override fun showLoadingState(isLoading: Boolean) {
        new_feed_progress_bar?.show(isLoading)
        new_feed_error_message_text_view?.show(!isLoading)
        new_feed_add_button?.isEnabled = !isLoading
        new_feed_url_edit_text?.isEnabled = !isLoading
    }
}
