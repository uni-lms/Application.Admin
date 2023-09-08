package ru.unilms.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "authParameters")