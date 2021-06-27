package com.divya_prakash.cliff.processor

interface Processor<in T, out Y> {
    suspend fun process(t: T): Y
    suspend fun process():Y
}