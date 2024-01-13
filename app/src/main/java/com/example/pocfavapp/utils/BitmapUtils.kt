package com.example.pocfavapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.util.Base64.encodeToString
import java.io.ByteArrayOutputStream

object BitmapUtils {

    fun encodeBitmapToString(bitmap: Bitmap): String {
        val bao = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao)

        return encodeToString(bao.toByteArray(), DEFAULT)
    }

    fun decodeStringToBitmap(bitmapStr: String): Bitmap {
        val bitmapByteArray = decode(bitmapStr, DEFAULT)

        return BitmapFactory.decodeByteArray(bitmapByteArray, 0 ,bitmapByteArray.size)
    }
}