package work.edwinlib.android.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun Float.toPrice(): String {
    val format = DecimalFormat("###,##0.00")
    return format.format(this)
}

fun Long.format(formatStr: String): String {
    val format = SimpleDateFormat(formatStr, Locale.getDefault())
    return format.format(this)
}

fun Date.format(formatStr: String): String {
    return this.time.format(formatStr)
}

fun String.isOnlyEmoji(): Boolean {
    for (char in this) {
        if (!char.isEmoji())
            return false
    }
    return true
}

fun Char.isEmoji(): Boolean {
    val codePoint = this.toInt()
    return !((codePoint == 0x0) ||
            (codePoint == 0x9) ||
            (codePoint == 0xA) ||
            (codePoint == 0xD) ||
            ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
            ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
            ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)))
}