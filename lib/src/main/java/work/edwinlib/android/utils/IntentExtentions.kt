package work.edwinlib.android.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

inline fun <reified T : Activity> Context.startActivity(vararg params: Pair<String, Any>) {
    startActivity(Intent(this, T::class.java).apply {
        putExtras(Bundle().put(*params))
    })
}

fun Bundle.put(vararg params: Pair<String, Any?>): Bundle {
    params.forEach {
        when (val value = it.second) {
            is Boolean -> putBoolean(it.first, value)
            is Int -> putInt(it.first, value)
            is Long -> putLong(it.first, value)
            is Double -> putDouble(it.first, value)
            is String -> putString(it.first, value)
            is BooleanArray -> putBooleanArray(it.first, value)
            is IntArray -> putIntArray(it.first, value)
            is LongArray -> putLongArray(it.first, value)
            is DoubleArray -> putDoubleArray(it.first, value)
            is Parcelable -> putParcelable(it.first, value)
            is Serializable -> putSerializable(it.first, value)
        }
    }
    return this
}