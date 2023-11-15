package ru.unilms.data

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

class PreferencesKeys {
    companion object {
        val ApiUri: Preferences.Key<String> = stringPreferencesKey("apiUri")
        val Token: Preferences.Key<String> = stringPreferencesKey("token")
    }
}