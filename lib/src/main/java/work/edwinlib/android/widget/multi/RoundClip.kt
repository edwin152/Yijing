package work.edwinlib.android.widget.multi

import android.graphics.Path
import kotlin.math.min

class RoundClip(
    private val radiusLeftTop: Float,
    private val radiusRightTop: Float,
    private val radiusLeftBottom: Float,
    private val radiusRightBottom: Float,
) : Multi.Clip {

    override fun getPath(width: Int, height: Int): Path {
        val w = width.toFloat()
        val h = height.toFloat()
        val radiusLeftTop = min(this.radiusLeftTop, min(w / 2f, h / 2f))
        val radiusRightTop = min(this.radiusRightTop, min(w / 2f, h / 2f))
        val radiusLeftBottom = min(this.radiusLeftBottom, min(w / 2f, h / 2f))
        val radiusRightBottom = min(this.radiusRightBottom, min(w / 2f, h / 2f))
        return Path().apply {
            moveTo(0f, radiusLeftTop)
            arcTo(
                0f, 0f,
                radiusLeftTop * 2, radiusLeftTop * 2,
                180f, 90f, false
            )
            lineTo(w - radiusRightTop, 0f)
            arcTo(
                w - radiusRightTop * 2, 0f,
                w, radiusRightTop * 2,
                270f, 90f, false
            )
            lineTo(w, h - radiusRightBottom)
            arcTo(
                w - radiusRightBottom * 2, h - radiusRightBottom * 2,
                w, h,
                0f, 90f, false
            )
            lineTo(radiusLeftBottom, h)
            arcTo(
                0f, h - radiusLeftBottom * 2,
                radiusLeftBottom * 2, h,
                90f, 90f, false
            )
            lineTo(0f, radiusLeftTop)
        }
    }

    override fun getStrokePath(width: Int, height: Int, strokeWidth: Float): Path {
        val w = width.toFloat()
        val h = height.toFloat()
        val offset = strokeWidth / 2
        val radiusLeftTop = min(this.radiusLeftTop, min(w / 2f, h / 2f))
        val radiusRightTop = min(this.radiusRightTop, min(w / 2f, h / 2f))
        val radiusLeftBottom = min(this.radiusLeftBottom, min(w / 2f, h / 2f))
        val radiusRightBottom = min(this.radiusRightBottom, min(w / 2f, h / 2f))
        return Path().apply {
            moveTo(offset, radiusLeftTop + offset)
            arcTo(
                offset, offset,
                radiusLeftTop * 2 + offset, radiusLeftTop * 2 + offset,
                180f, 90f, false
            )
            lineTo(w - radiusRightTop - offset, offset)
            arcTo(
                w - radiusRightTop * 2 - offset, offset,
                w - offset, radiusRightTop * 2 + offset,
                270f, 90f, false
            )
            lineTo(w - offset, h - radiusRightBottom - offset)
            arcTo(
                w - radiusRightBottom * 2 - offset, h - radiusRightBottom * 2 - offset,
                w - offset, h - offset,
                0f, 90f, false
            )
            lineTo(radiusLeftBottom + offset, h - offset)
            arcTo(
                offset, h - radiusLeftBottom * 2 - offset,
                radiusLeftBottom * 2 + offset, h - offset,
                90f, 90f, false
            )
            lineTo(offset, radiusLeftTop + offset)
        }
    }
}