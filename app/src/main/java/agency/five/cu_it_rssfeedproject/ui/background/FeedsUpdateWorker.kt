package agency.five.cu_it_rssfeedproject.ui.background

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class FeedsUpdateWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {

        Log.i("FeedsUpdateWorker", "Running feeds update background work")
        return Result.success()
    }
}