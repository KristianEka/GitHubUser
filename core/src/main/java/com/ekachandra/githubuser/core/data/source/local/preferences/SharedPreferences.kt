package com.ekachandra.githubuser.core.data.source.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SharedPreferences(
    private val context: Context,
) {

    object PreferencesKey {
        const val THEME_SETTING_KEY = "theme_setting"
    }

    private val Context.sharedPreferences: DataStore<Preferences> by preferencesDataStore(
        PreferencesKey.THEME_SETTING_KEY
    )

    private val themeKey = booleanPreferencesKey(PreferencesKey.THEME_SETTING_KEY)

    fun getThemeSetting(): Flow<Boolean> {
        return context.sharedPreferences.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        context.sharedPreferences.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }
}