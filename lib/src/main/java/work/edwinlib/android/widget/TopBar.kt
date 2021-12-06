package work.edwinlib.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.setPadding
import work.edwinlib.android.R
import work.edwinlib.android.utils.dip
import work.edwinlib.android.widget.multi.MultiConstraintLayout

class TopBar : MultiConstraintLayout {

    private val leftViewList = ArrayList<View>()
    private val rightViewList = ArrayList<View>()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        if (isInEditMode) addBack()
    }

    fun addLeftView(view: View, listener: OnClickListener = OnClickListener {}) {
        view.setOnClickListener(listener)
        view.id = View.generateViewId()
        leftViewList.add(view)

        val layoutParams = view.layoutParams as LayoutParams
        layoutParams.topToTop = LayoutParams.PARENT_ID
        layoutParams.bottomToBottom = LayoutParams.PARENT_ID
        if (leftViewList.isEmpty()) {
            layoutParams.startToStart = LayoutParams.PARENT_ID
        } else {
            layoutParams.startToEnd = leftViewList.last().id
        }
        layoutParams.dimensionRatio = "w,1:1"
        view.layoutParams = layoutParams
        addView(view)
    }

    fun addRightView(view: View, listener: OnClickListener = OnClickListener {}) {
        view.setOnClickListener(listener)
        view.id = View.generateViewId()
        rightViewList.add(view)

        val layoutParams = view.layoutParams as LayoutParams
        layoutParams.topToTop = LayoutParams.PARENT_ID
        layoutParams.bottomToBottom = LayoutParams.PARENT_ID
        if (rightViewList.isEmpty()) {
            layoutParams.endToEnd = LayoutParams.PARENT_ID
        } else {
            layoutParams.endToStart = rightViewList.last().id
        }
        layoutParams.dimensionRatio = "w,1:1"
        view.layoutParams = layoutParams
        addView(view)
    }

    fun addBack(isDarkMode: Boolean = false, listener: OnClickListener = OnClickListener {}) {
        val back = AppCompatImageView(context)
        back.setPadding(dip(16))
        back.setImageResource(if (isDarkMode) R.drawable.ic_back_dark else R.drawable.ic_back)
        back.layoutParams = LayoutParams(dip(48), dip(48))
        addLeftView(back, listener)
    }
}