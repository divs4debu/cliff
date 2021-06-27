package com.divya_prakash.cliff.provider

import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class NetworkDataProvider(private val clientFactory: NetworkClientFactory): DataProvider {
    private val networkClient: OkHttpClient = clientFactory.create()

    override suspend fun getImageFor(path: String): Response {
        val request  = buildRequest(path)
        // added delay for testing purpose only 
        // TODO: 27/06/2021  Must remove this in productions 
        delay(3000)
        return networkClient.newCall(request).execute()
    }

    private fun buildRequest(url: String): Request {
        return Request.Builder()
            .url(url)
            .build()
    }

    interface NetworkClientFactory {
        fun create(): OkHttpClient
    }
}