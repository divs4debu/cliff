package com.divya_prakash.cliff.cache

import android.graphics.Bitmap

interface ICache {
    suspend fun getImageForKey(key: String): Bitmap?
    suspend fun saveImageFor(key: String, bitmap: Bitmap)
    suspend fun removeKey(key: String)
    suspend fun clear()
    fun getSize(): Int
    fun getMaxSize(): Int
}