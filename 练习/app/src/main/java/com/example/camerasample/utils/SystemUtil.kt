package com.example.camerasample.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import java.io.File
import java.io.FileNotFoundException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class SystemUtil {
    companion object {
        fun getPackageName(context: Context): String {
            try {
                var packageManager: PackageManager = context.packageManager
                var packageInfo: PackageInfo = packageManager.getPackageInfo(context.packageName,0)
                return packageInfo.packageName
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        fun formatTime(time:Long): String {
            var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var curDate = Date(time)
            return formatter.format(curDate)
        }

        fun formatTime(time:Long,file:String): String {
            var formatter = SimpleDateFormat("yyyyMMddHHmmss")
            var curDate = Date(time)
            return formatter.format(curDate) + file
        }

        fun formatRandom(i: Int): String {
            var s: String = i.toString() + ""
            if (s.length == 1) {
                return "000$s"
            } else if (s.length == 2) {
                return "0$s"
            } else {
                return s
            }
        }

        fun twoPointDistance(event: MotionEvent?): Float {
            if (event == null) {
                return 0f
            }
            var x = event.getX(0) - event.getX(1)
            var y = event.getY(0) - event.getY(1)

            return Math.sqrt((x * x + y * y).toDouble()).toFloat()
        }

        fun saveAlbum(path: String, name: String, context: Context) {
            try {
                MediaStore.Images.Media.insertImage(context.contentResolver, path, name, null)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.parse("file://$path")))
        }

        fun copyPicture(srcPath: String,dstPath: String, name: String) {
            val oldFile = File(srcPath)
            val newFile = File(dstPath + name)
            Log.d("ssd",newFile.name)
            newFile.deleteOnExit()
            newFile.createNewFile()
            var c = -1
            val buffer = ByteArray(1024 * 1000)
            val inputStream = oldFile.inputStream()
            val now = System.currentTimeMillis()
            while ({ c = inputStream.read(buffer);c}() > 0) {
                newFile.appendBytes(buffer.copyOfRange(0,c))
            }
        }
    }
}