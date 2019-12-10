package agency.five.cu_it_rssfeedproject.app

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.show(show: Boolean = true) {
    if (visibility == VISIBLE && show) return
    if (visibility == GONE && !show) return
    visibility = if (show) VISIBLE else GONE
}

fun View.hide() {
    if (visibility == GONE) return
    visibility = GONE
}