package work.edwin.yijing.ui.divination

import android.content.Context
import work.edwinlib.android.utils.startActivity

//val DivinationActivity.KEY_ID: String get() = "id"

fun Context.launchDivination() {
    startActivity<DivinationActivity>()
}