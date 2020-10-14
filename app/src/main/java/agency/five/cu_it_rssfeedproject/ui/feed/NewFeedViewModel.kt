package agency.five.cu_it_rssfeedproject.ui.feed

import agency.five.cu_it_rssfeedproject.domain.interactor.AddNewFeedUseCase
import agency.five.cu_it_rssfeedproject.ui.common.AppSchedulers
import agency.five.cu_it_rssfeedproject.ui.common.BaseViewModel

class NewFeedViewModel(
    private val addNewFeedUseCase: AddNewFeedUseCase,
    private val schedulers: AppSchedulers
) :
    BaseViewModel(), NewFeedContract.ViewModel {

    private var isLoading = false

    override fun getLoadingState() = isLoading

    override fun setLoadingState(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    override fun addNewFeed(feedUrl: String) = addNewFeedUseCase.execute(feedUrl)
        .observeOn(schedulers.main())
        .subscribeOn(schedulers.background())
}