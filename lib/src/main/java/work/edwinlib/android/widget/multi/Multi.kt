package work.edwinlib.android.widget.multi

import android.graphics.Path

interface Multi {

    var clip: Clip?

    interface Clip {
        fun getPath(width: Int, height: Int): Path

        fun getStrokePath(width: Int, height: Int, strokeWidth: Float): Path?
    }
}