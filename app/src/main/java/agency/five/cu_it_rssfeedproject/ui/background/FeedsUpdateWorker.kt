package agency.five.cu_it_rssfeedproject.ui.background

import agency.five.cu_it_rssfeedproject.device.notification.Notifications
import agency.five.cu_it_rssfeedproject.domain.interactor.AddFeedItemsToFeedUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.GetFeedsUseCase
import agency.five.cu_it_rssfeedproject.domain.interactor.GetNewFeedItemsCountUseCase
import agency.five.cu_it_rssfeedproject.ui.notification.NotificationFactory
import android.app.PendingIntent
import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Completable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val NEW_FEED_ITEMS_NOTIFICATION_ID = 1234

class FeedsUpdateWorker(context: Context, workerParams: WorkerParameters) :
    RxWorker(context, workerParams), KoinComponent {

    private val getNewFeedItemsCountUseCase: GetNewFeedItemsCountUseCase by inject()
    private val getFeedsUseCase: GetFeedsUseCase by inject()
    private val addFeedItemsToFeedUseCase: AddFeedItemsToFeedUseCase by inject()
    private val notifications: Notifications by inject()
    private val notificationFactory: NotificationFactory by inject()
    private val notificationPendingIntent: PendingIntent by inject()

    override fun createWork(): Single<Result> {
        return getNewFeedItemsCountUseCase.execute()
            .first(0L)
            .flatMap { countBefore ->
                getFeedsUseCase.execute().first(emptyList())
                    .flatMapCompletable { feeds ->
                        Completable.merge {
                            feeds.map { feed ->
                                addFeedItemsToFeedUseCase.execute(feed)
                            }
                        }
                    }
                    .andThen(getNewFeedItemsCountUseCase.execute()
                        .firstOrError()
                        .map { countAfter -> countAfter > countBefore })
            }
            .doOnSuccess { shouldShowNotification -> if (shouldShowNotification) showNotification() }
            .map { Result.success() }
    }

    private fun showNotification() = notifications.show(
        NEW_FEED_ITEMS_NOTIFICATION_ID,
        notificationFactory.createNewFeedItemsNotification(notificationPendingIntent)
    )
}