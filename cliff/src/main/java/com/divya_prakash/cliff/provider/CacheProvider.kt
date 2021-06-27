package com.divya_prakash.cliff.provider

import com.divya_prakash.cliff.cache.ICache

interface CacheProvider {
    fun cache(): ICache
}