package agency.five.cu_it_rssfeedproject.ui.background

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

object FeedsUpdateWorkRequestFactory {

    fun createWorkRequest(repeatInterval: Long, repeatIntervalTimeUnit: TimeUnit): WorkRequest {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()

        return PeriodicWorkRequestBuilder<FeedsUpdateWorker>(
            repeatInterval,
            repeatIntervalTimeUnit
        ).setConstraints(constraints).build()
    }
}