package com.divya_prakash.cliff.provider

import android.graphics.Bitmap
import okhttp3.Response


interface DataProvider {
    suspend fun getImageFor(path: String): Response
}