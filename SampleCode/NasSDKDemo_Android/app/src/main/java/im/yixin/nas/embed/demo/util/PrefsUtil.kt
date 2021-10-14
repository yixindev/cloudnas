package im.yixin.nas.embed.demo.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Created by jixia.cai on 2021/3/3 1:03 PM
 */
object PrefsUtil {

    const val PREF_NAME_DEFAULT = "nas_demo_prefs"

    val sGson = GsonBuilder().create()

    fun <T> save(context: Context, key: String, value: T?): Boolean {
        val prefs = ensurePrefs(context)
        return prefs.edit().also {
            if (value == null) {
                it.remove(key)
            } else if (value is Int) {
                it.putInt(key, value)
            } else if (value is Long) {
                it.putLong(key, value)
            } else if (value is Boolean) {
                it.putBoolean(key, value)
            } else if (value is Float) {
                it.putFloat(key, value)
            } else if (value is String) {
                it.putString(key, value)
            } else {
                try {
                    val json = sGson.toJson(value)
                    it.putString(key, json)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    return false
                }
            }
        }.commit()
    }

    inline fun <reified T> read(context: Context, key: String, defValue: T?): T? {
        val prefs = ensurePrefs(context)
        if (!prefs.contains(key)) {
            return defValue
        }
        val value = prefs.getString(key, null) ?: return null
        try {
            return sGson.fromJson(value, T::class.java)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return defValue
    }

    fun remove(context: Context, key: String): Boolean {
        val prefs = ensurePrefs(context)
        return prefs.edit().remove(key).commit()
    }

    inline fun ensurePrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME_DEFAULT, Context.MODE_PRIVATE)
    }

    fun clearAll(context: Context): Boolean {
        return ensurePrefs(context).edit().clear().commit()
    }
}