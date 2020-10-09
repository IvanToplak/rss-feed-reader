package agency.five.cu_it_rssfeedproject

import agency.five.cu_it_rssfeedproject.ui.common.KoinActivity
import agency.five.cu_it_rssfeedproject.ui.router.Router
import android.os.Bundle
import android.view.Menu
import org.koin.core.parameter.parametersOf

class MainActivity : KoinActivity() {

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {

        router = get(parameters = { parametersOf(supportFragmentManager) })

        setupTitleProvider()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            router.showAllFeedsScreen()
            addTitle(getString(R.string.app_name))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}
