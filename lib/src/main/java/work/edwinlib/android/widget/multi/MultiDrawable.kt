package work.edwinlib.android.widget.multi

import android.graphics.*
import android.graphics.drawable.Drawable

open class MultiDrawable : Drawable(), Multi {

    var width: Int = 0
    var height: Int = 0
    var angle: Int = 0
    var colorEnd: Int = 0
    var colorStart: Int = 0
    var strokeColor: Int = 0
    var strokeWidth: Float = 0f
    var radiusLeftTop: Float = 0f
    var radiusRightTop: Float = 0f
    var radiusLeftBottom: Float = 0f
    var radiusRightBottom: Float = 0f

    override var clip: Multi.Clip? = null
    private var paint = Paint()
    private var strokePaint = Paint()

    override fun draw(canvas: Canvas) {
        if (clip == null) {
            clip = RoundClip(radiusLeftTop, radiusRightTop, radiusLeftBottom, radiusRightBottom)
        }
        drawGradient(canvas)
        drawStroke(canvas)
    }

    override fun setAlpha(alpha: Int) = Unit
    override fun setColorFilter(colorFilter: ColorFilter?) = Unit
    override fun getOpacity() = PixelFormat.UNKNOWN

    private fun drawGradient(canvas: Canvas) {
        val x0 = when (angle) {
            0 -> 0f
            90 -> 0f
            180 -> width.toFloat()
            270 -> 0f
            else -> 0f
        }
        val y0 = when (angle) {
            0 -> 0f
            90 -> height.toFloat()
            180 -> 0f
            270 -> 0f
            else -> 0f
        }
        val x1 = when (angle) {
            0 -> width.toFloat()
            90 -> 0f
            180 -> 0f
            270 -> 0f
            else -> width.toFloat()
        }
        val y1 = when (angle) {
            0 -> 0f
            90 -> 0f
            180 -> 0f
            270 -> height.toFloat()
            else -> height.toFloat()
        }
        paint.style = Paint.Style.FILL
        paint.shader = LinearGradient(x0, y0, x1, y1, colorStart, colorEnd, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true

        val path = clip!!.getPath(width, height)
        canvas.drawPath(path, paint)
    }

    private fun drawStroke(canvas: Canvas) {
        strokePaint.isAntiAlias = true
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = strokeWidth
        strokePaint.color = strokeColor

        val path = clip!!.getStrokePath(width, height, strokeWidth) ?: return
        canvas.drawPath(path, strokePaint)
    }
}