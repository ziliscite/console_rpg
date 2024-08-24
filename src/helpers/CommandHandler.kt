package helpers

import SERVER
import config.Cache
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import okhttp3.OkHttpClient
import okhttp3.Request

object CommandHandler {
    fun handle(route: String, cache: Cache, callback: (String) -> Unit) {
        runBlocking {
            launch {
                yield()
                handleResponse(route, cache, callback)
            }

            launch {
                loading()
                yield()
            }
        }
    }

    private fun handleResponse(route: String, cache: Cache, callback: (String) -> Unit){
        getRequest(endpoint = route).onSuccess {
            try {
                cacheResponse(route, it, cache)
                callback(it)
            } catch (e: Exception) {
                println("${e.message}")
            }
        }.onFailure {
            println("${it.message}")
        }
    }

    private val client = OkHttpClient()
    fun getRequest(endpoint: String): Result<String> {
        val request = Request.Builder().get().url(SERVER+endpoint).build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        return if (responseBody != null && response.code != 404) {
            Result.success(responseBody)
        } else {
            Result.failure(Exception("endpoint: $endpoint ${response.message}"))
        }
    }

    private fun loading() {
        print("loading")
        // what if we check every 500 ms if the getRequest already send a result
        // if haven't, we can print("."), if its ready, then we stop
        // ye max print would be 3 times, but after that, it will stuck there
        // instead of changing line

        // my ass
        for (i in 0..2) {
            Thread.sleep(500)
            print(".")
        }
        println()
    }

    private fun cacheResponse(route: String, response: String, cache: Cache) {
        cache.addCache(route, response)
    }
}