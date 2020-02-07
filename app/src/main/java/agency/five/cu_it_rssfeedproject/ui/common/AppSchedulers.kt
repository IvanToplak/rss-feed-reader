package agency.five.cu_it_rssfeedproject.ui.common

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulers {

    fun main(): Scheduler = AndroidSchedulers.mainThread()

    fun background(): Scheduler = Schedulers.io()
}