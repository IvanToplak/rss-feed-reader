package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.R
import agency.five.cu_it_rssfeedproject.app.show
import agency.five.cu_it_rssfeedproject.ui.common.BaseFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_new_feed.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewFeedFragment : BaseFragment(), NewFeedContract.View {

    private val viewModel: NewFeedContract.ViewModel by viewModel<NewFeedViewModel>()

    companion object {
        const val TAG = "newFeed"
        private const val INSERT_ERROR_MESSAGE = "Error inserting feed"
        fun newInstance() = NewFeedFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_new_feed, container, false)

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupBackground()
        setupButtons()
        showLoadingState(viewModel.getLoadingState())
        showErrorMessage(false)
    }

    private fun setupBackground() {
        new_feed_container.setOnClickListener {
            viewModel.back()
        }
    }

    private fun setupButtons() {
        new_feed_add_button.setOnClickListener {
            addNewFeed()
        }
    }

    private fun addNewFeed() {
        setLoadingState()
        addDisposable(viewModel.addNewFeed(new_feed_url_edit_text.text.toString())
            .subscribeBy(
                onComplete = {
                    setLoadingState(false)
                    showErrorMessage(false)
                    viewModel.back()
                },
                onError = { error ->
                    setLoadingState(false)
                    showErrorMessage()
                    Log.e(
                        TAG,
                        INSERT_ERROR_MESSAGE,
                        error
                    )
                }
            )
        )
    }

    private fun showErrorMessage(isError: Boolean = true) {
        new_feed_error_message_text_view?.show(isError)
    }

    private fun showLoadingState(isLoading: Boolean = true) {
        new_feed_progress_bar?.show(isLoading)
        new_feed_error_message_text_view?.show(!isLoading)
        new_feed_add_button?.isEnabled = !isLoading
        new_feed_url_edit_text?.isEnabled = !isLoading
    }

    private fun setLoadingState(isLoading: Boolean = true) {
        viewModel.setLoadingState(isLoading)
        showLoadingState(viewModel.getLoadingState())
    }
}
