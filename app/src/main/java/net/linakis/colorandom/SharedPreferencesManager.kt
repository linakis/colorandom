package net.linakis.colorandom

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    const val PREF_NAME = "ColorPrefs"
    private const val KEY_RANDOM_COLOR = "randomColor"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveRandomColor(color: Int) {
        if (::sharedPreferences.isInitialized.not()) {
            throw IllegalStateException("SharedPreferencesManager.init() must be called before using any functionality.")
        }
        sharedPreferences.edit().putInt(KEY_RANDOM_COLOR, color).apply()
    }

    fun loadRandomColor(): Int {
        if (::sharedPreferences.isInitialized.not()) {
            throw IllegalStateException("SharedPreferencesManager.init() must be called before using any functionality.")
        }
        return sharedPreferences.getInt(KEY_RANDOM_COLOR, 0)
    }
}