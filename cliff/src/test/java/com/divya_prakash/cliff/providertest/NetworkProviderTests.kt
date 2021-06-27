package com.divya_prakash.cliff.providertest

import com.divya_prakash.cliff.provider.DataProvider
import com.divya_prakash.cliff.provider.NetworkDataProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class NetworkProviderTests {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val scope = CoroutineScope(Dispatchers.IO)

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
    val dataProvider: DataProvider = NetworkDataProvider()


    @Test
    fun `is data received from url`() {
        runBlocking {
                val response =
                    dataProvider.getImageFor("https://cdn.shibe.online/shibes/908cf3bfeb6356fa1f7ebe69e7d64753a95f9528.jpg")
                println(response.code.toString())
        }
    }
}