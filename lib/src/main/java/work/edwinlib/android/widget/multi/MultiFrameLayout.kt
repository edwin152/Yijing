package work.edwinlib.android.widget.multi

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout
import work.edwinlib.android.R

open class MultiFrameLayout : FrameLayout, Multi {

    private var angle = 0
    private var colorEnd = 0xffffff
    private var colorStart = 0xffffff
    private var strokeColor = 0xffffff
    private var strokeWidth = 0f
    private var radiusLeftTop = 0f
    private var radiusRightTop = 0f
    private var radiusLeftBottom = 0f
    private var radiusRightBottom = 0f

    override var clip: Multi.Clip? = null
        set(clip) {
            field = clip
            multiDrawable.clip = clip
        }
    private var multiDrawable = MultiDrawable()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    @SuppressLint("CustomViewStyleable")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.Multi)

        angle = a.getInt(R.styleable.Multi_multi_angle, angle)
        val colorBg = a.getColor(R.styleable.Multi_multi_colorBg, 0xffffff)
        colorEnd = a.getColor(R.styleable.Multi_multi_colorEnd, colorBg)
        colorStart = a.getColor(R.styleable.Multi_multi_colorStart, colorBg)
        strokeColor = a.getColor(R.styleable.Multi_multi_strokeColor, strokeColor)
        strokeWidth = a.getDimension(R.styleable.Multi_multi_strokeWidth, strokeWidth)
        val radius = a.getDimension(R.styleable.Multi_multi_radius, 0f)
        radiusLeftTop = a.getDimension(R.styleable.Multi_multi_radiusLeftTop, radius)
        radiusRightTop = a.getDimension(R.styleable.Multi_multi_radiusRightTop, radius)
        radiusLeftBottom = a.getDimension(R.styleable.Multi_multi_radiusLeftBottom, radius)
        radiusRightBottom = a.getDimension(R.styleable.Multi_multi_radiusRightBottom, radius)

        a.recycle()
    }

    override fun draw(canvas: Canvas?) {
        if (canvas != null) {
            multiDrawable.width = width
            multiDrawable.height = height
            multiDrawable.angle = angle
            multiDrawable.colorEnd = colorEnd
            multiDrawable.colorStart = colorStart
            multiDrawable.strokeColor = strokeColor
            multiDrawable.strokeWidth = strokeWidth
            multiDrawable.radiusLeftTop = radiusLeftTop
            multiDrawable.radiusRightTop = radiusRightTop
            multiDrawable.radiusLeftBottom = radiusLeftBottom
            multiDrawable.radiusRightBottom = radiusRightBottom
            multiDrawable.draw(canvas)
        }
        super.draw(canvas)
    }
}