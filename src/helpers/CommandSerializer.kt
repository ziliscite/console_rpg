package helpers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CommandSerializer {
    val gson = Gson()

    // Generic function to deserialize and display response
    inline fun <reified T> getResponse(response: String): T {
        val result = gson.fromJson<T>(response, object: TypeToken<T>() {}.type)
        return result
    }
}