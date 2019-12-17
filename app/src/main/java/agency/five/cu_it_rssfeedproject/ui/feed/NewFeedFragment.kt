package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.show
import agency.five.cu_it_rssfeedproject.di.ObjectGraph
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_new_feed.*

class NewFeedFragment : Fragment(), NewFeedContract.View {

    private lateinit var presenter: NewFeedContract.Presenter

    companion object {
        const val TAG = "newFeed"
        fun newInstance() = NewFeedFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPresenter()
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
        showErrorMessage(false)
        showProgressBar(false)
        enableAddButton(true)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun setupPresenter() {
        presenter = ObjectGraph.getNewFeedPresenter(this)
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

    override fun showErrorMessage(show: Boolean) {
        new_feed_error_message_text_view?.show(show)
    }

    override fun showProgressBar(show: Boolean) {
        new_feed_progress_bar?.show(show)
    }

    override fun enableAddButton(enable: Boolean) {
        new_feed_add_button?.isEnabled = enable
    }
}
