package agency.five.cu_it_rssfeedproject.ui.background

import agency.five.cu_it_rssfeedproject.device.work.Work
import agency.five.cu_it_rssfeedproject.domain.background.FeedsUpdateScheduler
import androidx.work.WorkRequest

class FeedsUpdateSchedulerImpl(
    private val feedsUpdateWorkRequest: WorkRequest,
    private val work: Work
) : FeedsUpdateScheduler {

    override fun scheduleFeedsBackgroundUpdates() = work.schedule(feedsUpdateWorkRequest)

    override fun cancelFeedsBackgroundUpdates() = work.cancel(feedsUpdateWorkRequest.id)
}