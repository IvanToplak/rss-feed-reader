package agency.five.cu_it_rssfeedproject.device.work

import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.*

class WorkImpl(private val workManager: WorkManager) : Work {

    override fun schedule(workRequest: WorkRequest) {
        workManager.enqueue(workRequest)
    }

    override fun cancel(workId: UUID) {
        workManager.cancelWorkById(workId)
    }
}