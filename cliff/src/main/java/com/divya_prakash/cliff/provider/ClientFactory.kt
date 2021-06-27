package com.divya_prakash.cliff.provider

import okhttp3.OkHttpClient

open class ClientFactory: NetworkDataProvider.NetworkClientFactory {
    override fun create(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}