@file:Suppress("unused")

package work.edwinlib.android.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.Window
import work.edwinlib.android.R

/**
 * View 事件扩展文件
 */

fun View.visible() {
    visibility = VISIBLE
}

fun View.invisible() {
    visibility = INVISIBLE
}

fun View.gone() {
    visibility = GONE
}

fun View.show(show: Boolean) {
    visibility = if (show) VISIBLE else GONE
}

fun View.isVisible() = visibility == VISIBLE

fun View.isInvisible() = visibility == INVISIBLE

fun View.isGone() = visibility == GONE

/**
 * 防误触单击事件
 */
fun View.onThrottleClick(wait: Long = 200, onClick: ((View) -> Unit)) {
    setOnClickListener { v ->
        val curr = System.currentTimeMillis()
        val lastClick = (v.getTag(R.id.click_stamp) as? Long) ?: 0
        if (curr - lastClick > wait) {
            v.setTag(R.id.click_stamp, curr)
            onClick(v)
        }
    }
}

/**
 * 双击事件
 */
fun View.onDoubleClick(wait: Long = 200, onClick: ((View) -> Unit)) {
    setOnClickListener { v ->
        val curr = System.currentTimeMillis()
        val lastClick = (v.getTag(R.id.double_click_stamp) as? Long) ?: 0
        v.setTag(R.id.double_click_stamp, curr)
        if (curr - lastClick < wait) {
            onClick(v)
        }
    }
}

/**
 * 单双击融合事件
 */
fun View.onMultipleClick(
    wait: Long = 200,
    onClick: (View) -> Unit,
    onDoubleClick: (View) -> Unit,
) {
    setOnClickListener { v ->
        val curr = System.currentTimeMillis()
        val lastClick = (v.getTag(R.id.multi_click_stamp) as? Long) ?: 0
        v.setTag(R.id.multi_click_stamp, curr)
        if (curr - lastClick > wait) {
            onClick(v)
        } else {
            onDoubleClick(v)
        }
    }
}

/**
 * 长按事件
 */
@SuppressLint("ClickableViewAccessibility")
fun View.onLongClick(
    pressDrawable: Drawable? = null,
    onLongClick: (View, x: Int, y: Int) -> Boolean
) {
    setOnLongClickListener { v ->
        val x = v.getTag(R.id.long_click_x) as Int
        val y = v.getTag(R.id.long_click_y) as Int
        background = v.getTag(R.id.long_click_background) as? Drawable
        onLongClick(v, x, y)
    }
    setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            v.setTag(R.id.long_click_x, event.rawX.toInt())
            v.setTag(R.id.long_click_y, event.rawY.toInt())
            v.setTag(R.id.long_click_background, background)
            pressDrawable?.let { background = it }
        }
        if (event.action == MotionEvent.ACTION_UP
            || event.action == MotionEvent.ACTION_CANCEL
            || event.action == MotionEvent.ACTION_OUTSIDE
            || event.action == MotionEvent.ACTION_SCROLL
        ) {
            val d = v.getTag(R.id.long_click_background)
            if (d is Drawable) background = d
        }
        false
    }
}