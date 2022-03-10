package com.demo.thenews.model.util.impl

import android.content.Context
import android.content.SharedPreferences
import com.demo.thenews.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface IPref {
    fun put(key: String = "NEWS", value: Any)
    fun str(key: String = "NEWS", default: String = ""): String
    fun long(key: String = "NEWS", default: Long = -1): Long
    fun float(key: String = "NEWS", default: Float = -1f): Float
    fun int(key: String = "NEWS", default: Int = -1): Int
    fun bool(key: String = "NEWS", default: Boolean = false): Boolean
    fun clear()
}

class PrefImpl @Inject constructor(@ApplicationContext private val context: Context) : IPref {
    private val pref: SharedPreferences =
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}_pref", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()
    override fun put(key: String, value: Any) {
        when (value) {
            is String -> editor.putString(key, value).apply()
            is Int -> editor.putInt(key, value).apply()
            is Float -> editor.putFloat(key, value).apply()
            is Long -> editor.putLong(key, value).apply()
            is Boolean -> editor.putBoolean(key, value).apply()
            else -> false
        }
    }

    override fun str(key: String, default: String): String = pref.getString(key, default) ?: ""

    override fun long(key: String, default: Long): Long = pref.getLong(key, default)

    override fun float(key: String, default: Float): Float = pref.getFloat(key, default)

    override fun int(key: String, default: Int): Int = pref.getInt(key, default)

    override fun bool(key: String, default: Boolean): Boolean = pref.getBoolean(key, default)

    override fun clear() = pref.edit().clear().apply()

}