package work.edwinlib.android.widget

import android.content.Context
import android.view.Gravity.BOTTOM
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.app.AlertDialog
import work.edwinlib.android.R

open class FullScreenDialog(context: Context) : AlertDialog(context, R.style.FullScreenDialog) {

    var gravity = BOTTOM

    override fun show() {
        super.show()
        window?.let {
            it.setGravity(gravity)
            it.setLayout(MATCH_PARENT, WRAP_CONTENT)
            it.setBackgroundDrawable(null)
            it.decorView.setPadding(0, 0, 0, 0)
        }
    }
}