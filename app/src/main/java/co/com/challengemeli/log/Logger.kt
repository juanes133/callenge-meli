package co.com.challengemeli.log

import android.util.Log
import co.com.challengemeli.BuildConfig
import com.google.gson.JsonObject

class Logger {
    fun error(e: Exception) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Ocurrio un error", e)
        }
    }

    fun info(result: JsonObject) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, result.asJsonObject.toString())
        }
    }

    companion object {
        const val TAG = "CHALLENGE_MELI"
    }
}