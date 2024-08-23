package helper

import SERVER
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import okhttp3.OkHttpClient
import okhttp3.Request

object CommandHandler {
    // cachenya dipass lewat handle function, bukan callbacknya
        // ya, callbacknya juga harus dikasih parameter cache sih buat dipass dari handle
    fun handle(route: String, callback: (String) -> Unit) {
        runBlocking {
            launch {
                yield()
                val response = getRequest(endpoint = route)
                handleResult(response, callback)
            }

            launch {
                loading()
                yield()
            }
        }
    }

    private val client = OkHttpClient()
    private fun getRequest(endpoint: String): Result<String> {
        val request = Request.Builder().get().url(SERVER +endpoint).build()
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
        for (i in 0..2) {
            Thread.sleep(500)
            print(".")
        }
        println()
    }

    private fun handleResult(result: Result<String>, callback: (String) -> Unit) {
        result.onSuccess {
            try {
                callback(it)
            } catch (e: Exception) {
                println("Caught an error: ${e.message}")
            }
        }.onFailure {
            println("Caught an error: ${it.message}")
        }
    }
}