package beresident.prototype.beresidentuserapp.core.misc

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreTheme(private val context: Context) {

    //THEME
    //1. Auto
    //2. Light mode
    //3. Dark mode
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userEmail")
        val THEME = intPreferencesKey("theme")
    }

    val getTheme: Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[THEME] ?: 0
    }

    suspend fun saveTheme(isDarkTheme: Int){
        context.dataStore.edit { preferences ->
            preferences[THEME] = isDarkTheme
        }
    }



    /*companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userEmail")
        val THEME = booleanPreferencesKey("theme")
    }

    val getTheme: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[THEME] ?: null
    }

    suspend fun saveTheme(isDarkTheme: Boolean){
        context.dataStore.edit { preferences ->
            preferences[THEME] = isDarkTheme
        }
    }*/

}
