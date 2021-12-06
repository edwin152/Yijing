package work.edwin.yijing.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import work.edwin.yijing.model.Yao

class YaoView : View {

    private val paint = Paint()

    var yao = Yao.Yang
        set(value) {
            field = value
            invalidate()
        }
    var color = Color.BLACK
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        paint.isAntiAlias = true
        paint.color = color
        when (yao) {
            Yao.Yang -> canvas?.drawYang()
            Yao.Yin -> canvas?.drawYin()
        }
    }

    private fun Canvas.drawYang() {
        drawRect(clipBounds, paint)
    }

    private fun Canvas.drawYin() {
        val rect = clipBounds
        val width = rect.width()
        val height = rect.height()
        val w = width * 4 / 9
        val rectLeft = Rect(0, 0, w, height)
        val rectRight = Rect(width - w, 0, width, height)
        drawRect(rectLeft, paint)
        drawRect(rectRight, paint)
    }
}