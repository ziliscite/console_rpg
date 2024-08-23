package caching

class Cache {
    private val caches = hashMapOf<String, String>()

    fun getCache(route: String): String? {
        return caches[route]
    }

    fun addCache(route: String, data: String) {
        caches[route] = data
    }
}