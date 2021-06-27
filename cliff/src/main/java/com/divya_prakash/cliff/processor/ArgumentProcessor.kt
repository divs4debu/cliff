package com.divya_prakash.cliff.processor

import android.graphics.BitmapFactory
import com.divya_prakash.cliff.Cliff
import com.divya_prakash.cliff.cache.ICache
import com.divya_prakash.cliff.cache.getCacheKeyFor
import com.divya_prakash.cliff.provider.*
import kotlinx.coroutines.*

class ArgumentProcessor(private val arguments: Cliff.Arguments): Processor<Cliff.Arguments, Unit> {
    private val networkDataProvider = NetworkDataProvider(SingleClientFactory())
    private val cache: ICache = DiskCacheProvider(arguments.context).cache()
    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun process(t: Cliff.Arguments) {
        scope.launch {
            val cacheKey = getCacheKeyFor(t.url)
            val bitmap = cache.getImageForKey(cacheKey)
            if (bitmap != null) {
                withContext(Dispatchers.Main) { t.view.setImageBitmap(bitmap) }
            } else {
                val response = networkDataProvider.getImageFor(t.url)
                val stream = response.body?.byteStream()
                stream?.let {
                    val bitmap = BitmapFactory.decodeStream(stream)
                    if(!response.cacheControl.noStore) {
                        async { cache.saveImageFor(cacheKey, bitmap) }
                    }
                    withContext(Dispatchers.Main) { t.view.setImageBitmap(bitmap) }
                }

            }
        }
    }

    override suspend fun process() {
        scope.launch {
            val cacheKey = getCacheKeyFor(arguments.url)
            val bitmap = cache.getImageForKey(cacheKey)
            if (bitmap != null) {
                withContext(Dispatchers.Main) { arguments.view.setImageBitmap(bitmap) }
            } else {
                val response = networkDataProvider.getImageFor(arguments.url)
                val stream = response.body?.byteStream()
                stream?.let {
                    val bitmap = BitmapFactory.decodeStream(stream)
                    async { cache.saveImageFor(cacheKey, bitmap) }
                    withContext(Dispatchers.Main) { arguments.view.setImageBitmap(bitmap) }
                }

            }
        }
    }
}