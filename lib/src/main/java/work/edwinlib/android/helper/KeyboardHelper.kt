package work.edwinlib.android.helper

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import work.edwinlib.android.utils.dip
import kotlin.math.round

class KeyboardHelper(private val activity: Activity) {

    companion object {
        private const val SHOW_KEYBOARD_DELAY_TIME = 200L
        private const val KEYBOARD_VISIBLE_THRESHOLD_DP = 100
    }

    private val rootView by lazy {
        (activity.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup).getChildAt(0)
    }
    private val inputManager by lazy {
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    fun showKeyboard(editText: EditText, delay: Long = SHOW_KEYBOARD_DELAY_TIME) {
        if (!editText.requestFocus()) {
            return
        }
        if (delay > 0) {
            editText.postDelayed({
                inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
            }, delay)
        } else {
            inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideKeyboard(): Boolean {
        return inputManager.hideSoftInputFromWindow(rootView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun setKeyboardListener(listener: (Boolean, Int) -> Boolean) {
        setKeyboardListener(object : KeyboardListener {
            override fun onShow(show: Boolean, heightDiff: Int): Boolean {
                return listener(show, heightDiff)
            }
        })
    }

    fun setKeyboardListener(listener: KeyboardListener) {
        val layoutListener: OnGlobalLayoutListener = object : OnGlobalLayoutListener {
            private val r = Rect()
            private var wasOpened = false
            private val threshold = round(
                activity.dip(KEYBOARD_VISIBLE_THRESHOLD_DP).toDouble()
            ).toInt()

            override fun onGlobalLayout() {
                rootView.getWindowVisibleDisplayFrame(r)
                val heightDiff = rootView.rootView.height - r.height()
                val isOpen = heightDiff > threshold
                if (isOpen == wasOpened) {
                    // keyboard state has not changed
                    return
                }
                wasOpened = isOpen
                val removeListener = listener.onShow(isOpen, heightDiff)
                if (removeListener) {
                    rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        }
        rootView.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
        activity.application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
                if (activity == this@KeyboardHelper.activity) {
                    rootView.viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
                }
            }
        })
    }

    fun isKeyboardVisible(activity: Activity): Boolean {
        val r = Rect()
        val visibleThreshold = round(
            activity.dip(KEYBOARD_VISIBLE_THRESHOLD_DP).toDouble()
        ).toInt()
        rootView.getWindowVisibleDisplayFrame(r)
        val heightDiff = rootView.rootView.height - r.height()
        return heightDiff > visibleThreshold
    }

    interface KeyboardListener {
        fun onShow(show: Boolean, heightDiff: Int): Boolean
    }
}