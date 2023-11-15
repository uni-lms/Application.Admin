package ru.unilms.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {

    companion object {
        private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "authParameters")
    }

    val token: Flow<String> = context.authDataStore.data.map { preferences ->
        preferences[PreferencesKeys.Token] ?: ""
    }

    suspend fun updateToken(token: String) {
        context.authDataStore.edit { preferences ->
            preferences[PreferencesKeys.Token] = token
        }
    }

    val apiUri: Flow<String> = context.authDataStore.data.map { preferences ->
        preferences[PreferencesKeys.ApiUri] ?: ""
    }

    suspend fun updateApiUri(apiUri: String) {
        context.authDataStore.edit { preferences ->
            preferences[PreferencesKeys.ApiUri] = apiUri
        }
    }

}