package agency.five.cu_it_rssfeedproject.ui.router

import agency.five.cu_it_rssfeedproject.di.MAIN_ACTIVITY_SCOPE_ID
import org.koin.core.KoinComponent

class RouterProviderImpl : RouterProvider, KoinComponent {

    override fun getRouter() = getKoin().getScope(MAIN_ACTIVITY_SCOPE_ID).get<Router>()
}