package work.edwinlib.android.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore.Images.Media
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object BitmapUtils {

    @Suppress("DEPRECATION")
    fun saveToFile(activity: Activity, bitmap: Bitmap, dir: File, name: String): String {
        val image = File(dir, name)
        var outputStream: FileOutputStream? = null
        try {
            if (!dir.exists()) {
                dir.mkdirs()
            }
            outputStream = FileOutputStream(image)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()

            //保存
            val values = ContentValues()
            values.put(Media.DATA, image.absolutePath)
            values.put(Media.MIME_TYPE, "image/jpeg")
            activity.contentResolver.insert(Media.EXTERNAL_CONTENT_URI, values)
            //最后通知图库更新
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE) //扫描单个文件
            intent.data = Uri.parse("file://" + image.absolutePath)
            activity.sendBroadcast(intent)
        } catch (ignore: IOException) {
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return image.absolutePath
    }
}