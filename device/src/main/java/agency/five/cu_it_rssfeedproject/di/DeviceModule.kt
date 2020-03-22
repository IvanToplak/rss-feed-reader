package agency.five.cu_it_rssfeedproject.di

import agency.five.cu_it_rssfeedproject.device.work.Work
import agency.five.cu_it_rssfeedproject.device.work.WorkImpl
import org.koin.dsl.module

val deviceModule = module {

    single<Work> { WorkImpl(get()) }
}