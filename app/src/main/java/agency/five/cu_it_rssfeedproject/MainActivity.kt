package agency.five.cu_it_rssfeedproject

import agency.five.cu_it_rssfeedproject.di.MAIN_ACTIVITY_SCOPE
import agency.five.cu_it_rssfeedproject.di.MAIN_ACTIVITY_SCOPE_ID
import agency.five.cu_it_rssfeedproject.ui.common.ScreenTitleProvider
import agency.five.cu_it_rssfeedproject.ui.router.Router
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class MainActivity : AppCompatActivity() {

    private val screenTitleProvider: ScreenTitleProvider by inject()

    private lateinit var router: Router
    private lateinit var mainActivityScope: Scope

    override fun onCreate(savedInstanceState: Bundle?) {

        mainActivityScope =
            getKoin().createScope(MAIN_ACTIVITY_SCOPE_ID, named(MAIN_ACTIVITY_SCOPE))
        router = mainActivityScope.get { parametersOf(supportFragmentManager) }

        screenTitleProvider.registerSetTitleFunction { title ->
            supportActionBar?.title = title
        }

        screenTitleProvider.registerSetTitleVisibilityFunction { show ->
            if (show) {
                supportActionBar?.show()
            } else {
                supportActionBar?.hide()
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            router.showAllFeedsScreen()
            screenTitleProvider.addTitle(getString(R.string.app_name))
        }
    }

    override fun onDestroy() {
        mainActivityScope.close()
        super.onDestroy()
    }
}
