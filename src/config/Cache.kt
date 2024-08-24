package config

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes
import java.time.Duration
import java.time.LocalDateTime
import kotlin.time.toJavaDuration

class Cache {
    private val caches = hashMapOf<String, Pair<String, LocalDateTime>>()
    private val timeout = 5.minutes

    init {
        // Launch cache loop when cache is created
        GlobalScope.launch {
            cacheLoop()
        }
    }

    private fun getCache(route: String): String? {
        return caches[route]?.first
    }

    fun addCache(route: String, data: String) {
        val createdAt = LocalDateTime.now()
        caches[route] = Pair(data, createdAt)
    }

    fun displayFromCache(route: String, callback: (String) -> Unit): Boolean {
        val response = this.getCache(route)
        if (response != null) {
            callback(response)
            return true
        }

        return false
    }

    private suspend fun cacheLoop() {
        val tickerFlow = flow {
            while (true) {
                emit(System.currentTimeMillis())
                delay(30000L) // 30-second delay, so it wouldn't be too heavy for the program
            }
        }

        // Collect the flow and remove expired caches
        tickerFlow.collect {
            removeCache()
        }
    }

    private fun removeCache() {
        for (cache in caches) {
            // Check the duration between when the cache was made and now
            val durationSinceCache = Duration.between(cache.value.second, LocalDateTime.now())

            if (durationSinceCache > timeout.toJavaDuration()) {
                caches.remove(cache.key)
            }
        }
    }
}