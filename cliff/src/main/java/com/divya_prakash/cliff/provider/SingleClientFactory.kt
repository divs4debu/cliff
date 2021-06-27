package com.divya_prakash.cliff.provider

import okhttp3.OkHttpClient

class SingleClientFactory: ClientFactory() {
    object HttpClient {
        val client: OkHttpClient by lazy { OkHttpClient.Builder().build() }
    }

    override fun create(): OkHttpClient {
        return HttpClient.client
    }
}