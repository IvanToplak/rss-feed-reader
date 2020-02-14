package agency.five.cu_it_rssfeedproject.domain.di

import agency.five.cu_it_rssfeedproject.domain.interactor.*
import org.koin.dsl.module

val domainModule = module {

    single { GetFeedsUseCase(get()) }

    single { AddNewFeedUseCase(get()) }

    single { DeleteFeedUseCase(get()) }

    single { GetFeedItemsUseCase(get()) }

    single { AddFeedItemsToFeedUseCase(get()) }

    single { UpdateFeedItemIsNewStatusUseCase(get()) }

    single { GetFeedHasUnreadItemsStatusUseCase(get()) }
}