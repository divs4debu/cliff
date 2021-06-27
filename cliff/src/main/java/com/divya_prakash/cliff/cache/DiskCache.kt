package com.divya_prakash.cliff.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.jakewharton.disklrucache.DiskLruCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class DiskCache(private val directory: File, maxSize: Long) : ICache {
    private var diskLruCache: DiskLruCache? = null
    private val diskCacheLock = ReentrantLock()
    private val diskCacheLockCondition: Condition = diskCacheLock.newCondition()
    private var diskCacheStarting = true
    private val BUFFER_SIZE = DEFAULT_BUFFER_SIZE
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            diskCacheLock.withLock {
                val cacheDir = directory
                diskLruCache = DiskLruCache.open(cacheDir, 1,1, maxSize)
                diskCacheStarting = false
                diskCacheLockCondition.signalAll()
            }
        }
    }

    override suspend fun getImageForKey(key: String): Bitmap? {
        diskCacheLock.withLock {
            while (diskCacheStarting) {
                try {
                    diskCacheLockCondition.await()
                }catch (e: InterruptedException){}
            }
            var bitmap: Bitmap? = null
            val snapshot = diskLruCache?.get(key) ?: return null
            val bitmapStream = snapshot.getInputStream(0)
            if(bitmapStream != null) {
                val buffStream = BufferedInputStream(bitmapStream, BUFFER_SIZE)
                bitmap = BitmapFactory.decodeStream(buffStream)
            }
            return bitmap
        }
    }

    override suspend fun saveImageFor(key: String, bitmap: Bitmap) {
        scope.async {
            synchronized(diskCacheLock) {
                diskLruCache?.apply {
                    val snapshot = get(key)
                    if (snapshot == null) {
                        val editor = edit(key)
                        val outStream = BufferedOutputStream(editor.newOutputStream(0), BUFFER_SIZE)
                        val success = bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outStream)
                        if (success) {
                            flush()
                            editor.commit()
                        } else {
                            editor.abort()
                        }
                    } else {
                        snapshot.close()
                    }
                }
            }
        }.await()
    }

    override suspend fun removeKey(key: String) {
        scope.async {
            diskCacheLock.withLock {
                diskLruCache?.remove(key)
            }
        }
    }

    override suspend fun clear() {
        scope.async {
            synchronized(diskCacheLock) {
                diskLruCache?.apply { delete() }
            }
        }.await()
    }

    override fun getSize(): Int {
        return diskLruCache?.size()?.div(1024)?.toInt() ?: 0

    }

    override fun getMaxSize(): Int {
        return diskLruCache?.maxSize?.div(1024)?.toInt() ?: 0
    }
}