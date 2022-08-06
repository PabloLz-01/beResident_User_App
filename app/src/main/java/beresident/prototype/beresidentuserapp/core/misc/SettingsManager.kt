package beresident.prototype.beresidentuserapp.core.misc

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreTheme(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userEmail")
        val THEME = booleanPreferencesKey("theme")
    }

    val getTheme: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[THEME] ?: false
    }

    suspend fun saveTheme(isDarkTheme: Boolean){
        context.dataStore.edit { preferences ->
            preferences[THEME] = isDarkTheme
        }
    }
}
