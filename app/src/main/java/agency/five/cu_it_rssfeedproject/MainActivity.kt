package agency.five.cu_it_rssfeedproject

import agency.five.cu_it_rssfeedproject.di.ObjectGraph
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ObjectGraph.setScopedRouter(ObjectGraph.mainActivityScope, supportFragmentManager)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            ObjectGraph.getScopedRouter(ObjectGraph.mainActivityScope)?.showAllFeedsScreen()
        }
    }

    override fun onDestroy() {
        ObjectGraph.removeScopedRouter(ObjectGraph.mainActivityScope)
        super.onDestroy()
    }
}
