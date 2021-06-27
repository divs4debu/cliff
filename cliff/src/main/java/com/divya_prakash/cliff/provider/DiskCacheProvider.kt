package com.divya_prakash.cliff.provider

import android.content.Context
import com.divya_prakash.cliff.cache.DiskCache
import com.divya_prakash.cliff.cache.ICache
import com.divya_prakash.cliff.cache.getDiskCacheDir

class DiskCacheProvider(val context: Context): CacheProvider {
    object DiskLRUCache {
        private const val DISK_CACHE_SIZE: Long = 1024 * 1024 * 20
        fun get(context: Context): ICache = DiskCache(getDiskCacheDir(context, "madly-thumbnails"), DISK_CACHE_SIZE)
    }
    override fun cache(): ICache = DiskLRUCache.get(context)
}