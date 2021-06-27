package com.divya_prakash.cliff.cache

import android.graphics.Bitmap
import android.util.LruCache

class MemoryCache(maxSize: Int) : LruCache<String, Bitmap>(maxSize), ICache {
    override suspend fun getImageForKey(key: String): Bitmap? {
        return get(key)
    }

    override suspend fun saveImageFor(key: String, bitmap: Bitmap) {
        put(key, bitmap)
    }

    override suspend fun removeKey(key: String) {
        removeKey(key)
    }

    override suspend fun clear() {
        evictAll()
    }

    override fun getSize(): Int {
        return size()
    }

    override fun getMaxSize(): Int {
        return maxSize()
    }

    override fun sizeOf(key: String?, value: Bitmap?): Int {
        var size = 0
        value?.let { size = it.byteCount/ 1024 }
        return size
    }
}