package com.localstoragesantiago

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class PreferencesManager(private val context: Context) {

  suspend fun setValue(key: String, value: String) {
      val dataStoreKey = stringPreferencesKey(key)
      context.dataStore.edit { settings ->
        settings[dataStoreKey] = value
      }
  }

   fun getValue(key: String): Flow<String?> {
    val dataStoreKey = stringPreferencesKey(key)

    val exampleCounterFlow: Flow<String> = context.dataStore.data
      .map { preferences ->
        // No type safety.
        preferences[dataStoreKey] ?: "Vazio"
      }

    return exampleCounterFlow
  }
}

//
//class PreferencesManager(context: Context) {
//  val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
//
//  val _context = context
//  val scope = MainScope()
//
//  fun setValue(key:String, value: String) {
//    scope.launch {
//      _context.dataStore.edit { settings ->
//        val _key = stringPreferencesKey(key)
//
//        settings[_key] = value
//      }
//    }
//  }
//
//  fun getValue(key: String): Flow<String?> {
//    val dataStoreKey = stringPreferencesKey(key)
//    return _context.dataStore.data.map {
//      it.toString()
//    }
//  }
//
//}
