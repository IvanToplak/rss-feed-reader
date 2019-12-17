package agency.five.cu_it_rssfeedproject

import agency.five.cu_it_rssfeedproject.ui.feed.FeedsFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_layout, FeedsFragment.newInstance(), FeedsFragment.TAG)
                .commit()
        }
    }
}
