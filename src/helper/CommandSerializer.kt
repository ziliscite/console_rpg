package helper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CommandSerializer {
    val gson = Gson()
    /*
    fun <T> serialize(response: String, template: Class<T>) {
        val result = gson.fromJson(response, template::class.java)
        println(result)
    }
    */

    // Generic function to deserialize and display response
    inline fun <reified T> displayResponse(response: String): T {
        val result = gson.fromJson<T>(response, object: TypeToken<T>() {}.type)
        return result
    }
}