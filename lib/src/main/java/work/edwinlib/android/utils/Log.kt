package work.edwinlib.android.utils

import android.util.Log

object Log {

    fun v(any: Any?) {
        Log.v("edwin", any.toString())
    }

    fun d(any: Any?) {
        Log.d("edwin", any.toString())
    }

    fun i(any: Any?) {
        Log.i("edwin", any.toString())
    }

    fun w(any: Any?) {
        Log.w("edwin", any.toString())
    }

    fun e(any: Any?) {
        if (any is Throwable) {
            Log.e("edwin", null, any)
        } else {
            Log.e("edwin", any.toString())
        }
    }
}