package work.edwinlib.android.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Images.ImageColumns

@Suppress("DEPRECATION")
fun String.toUri(context: Context): Uri? {
    val cursor = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        arrayOf(ImageColumns._ID, ImageColumns.DATA),
        null, null, null,
    )
    if (cursor?.moveToFirst() == true) {
        do {
            val id = cursor.getString(cursor.getColumnIndex(ImageColumns._ID))
            val path = cursor.getString(cursor.getColumnIndex(ImageColumns.DATA))
            if (path == this) {
                return Uri.parse("content://media/external/images/media/$id")
            }
        } while (cursor.moveToNext())
    }
    cursor?.close()
    return null
}