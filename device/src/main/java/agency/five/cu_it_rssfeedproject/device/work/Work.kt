package agency.five.cu_it_rssfeedproject.device.work

import androidx.work.WorkRequest
import java.util.*

interface Work {

    fun schedule(workRequest: WorkRequest)

    fun cancel(workId: UUID)
}