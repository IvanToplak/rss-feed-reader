package agency.five.cu_it_rssfeedproject.domain.background

interface FeedsUpdateScheduler {

    fun scheduleFeedsBackgroundUpdates()

    fun cancelFeedsBackgroundUpdates()
}