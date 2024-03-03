package ru.aip.intern.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("auth")
    }

    private object PreferenceKeys {
        val apiKey = stringPreferencesKey("apiKey")
    }

    suspend fun saveApiKey(key: String) {
        context.datastore.edit { prefs ->
            prefs[PreferenceKeys.apiKey] = key
        }
    }

    val apiKey: Flow<String?> = context.datastore.data
        .map { prefs ->
            prefs[PreferenceKeys.apiKey] ?: ""
        }
}