package agency.five.cu_it_rssfeedproject.ui.background

import agency.five.cu_it_rssfeedproject.domain.interactor.AddFeedItemsToFeedUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedsUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.GetNewFeedItemsCountUseCase
import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class FeedsUpdateWorker(context: Context, workerParams: WorkerParameters) :
    RxWorker(context, workerParams), KoinComponent {

    private val getNewFeedItemsCountUseCase: GetNewFeedItemsCountUseCase by inject()
    private val getFeedsUseCase: GetFeedsUseCase by inject()
    private val addFeedItemsToFeedUseCase: AddFeedItemsToFeedUseCase by inject()

    override fun createWork(): Single<Result> {
        return getNewFeedItemsCountUseCase.execute()
            .first(0L)
            .map { countBefore ->
                getFeedsUseCase.execute().first(emptyList())
                    .map { feeds -> feeds.map { feed -> addFeedItemsToFeedUseCase.execute(feed) } }
                    .blockingGet()
                return@map countBefore
            }.map { countBefore ->
                getNewFeedItemsCountUseCase.execute()
                    .first(0L).map { countAfter ->
                        if (countAfter > countBefore) {
                            Log.i("FeedsUpdateWorker", "new items -> show notification")
                        } else {
                            Log.i("FeedsUpdateWorker", "there is no new items")
                        }
                    }.blockingGet()
            }.map {
                Result.success()
            }
    }
}