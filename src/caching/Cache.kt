package caching

class Cache {
    private val caches = hashMapOf<String, String>()

    private fun getCache(route: String): String? {
        return caches[route]
    }

    fun addCache(route: String, data: String) {
        caches[route] = data
    }

    fun displayFromCache(route: String, callback: (String) -> Unit) {
        val response = this.getCache(route)
        if (response != null) {
            callback(response)
            // Odd, but it works
            throw Exception("Cache hit")
        }
    }
}