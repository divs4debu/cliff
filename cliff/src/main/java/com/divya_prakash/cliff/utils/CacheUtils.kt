package com.divya_prakash.cliff.cache

import android.content.Context
import android.os.Environment
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


fun getDiskCacheDir(context: Context, uniqueName: String): File {
    val cachePath =
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
            || !Environment.isExternalStorageRemovable()
        ) {
            context.externalCacheDir?.path
        } else {
            context.cacheDir.path
        }

    return File(cachePath + File.separator + uniqueName)
}

fun getCacheKeyFor(url: String): String {
    return try {
        val mDigest: MessageDigest = MessageDigest.getInstance("MD5")
        mDigest.update(url.toByteArray())
        bytesToHexString(mDigest.digest())
    } catch (e: NoSuchAlgorithmException) {
        url.hashCode().toString()
    }
}

private fun bytesToHexString(bytes: ByteArray): String {
    val sb = StringBuilder()
    for (i in bytes.indices) {
        val hex = Integer.toHexString(0xFF and bytes[i].toInt())
        if (hex.length == 1) {
            sb.append('0')
        }
        sb.append(hex)
    }
    return sb.toString()
}