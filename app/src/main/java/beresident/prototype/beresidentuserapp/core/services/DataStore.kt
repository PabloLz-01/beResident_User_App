package beresident.prototype.beresidentuserapp.core.misc

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreTheme(private val context: Context) {

    //THEME //1. Auto //2. Light mode //3. Dark mode
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("theme")
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
}

class StoreUserCredentials(private val context: Context) {
    //SAVE USER CREDENTIALS -------------------
    companion object {
        private val Context.userStore: DataStore<Preferences> by preferencesDataStore("user")
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = stringPreferencesKey("password")
    }

    //EMAIL -----------------------------------
    val getEmail: Flow<String?> = context.userStore.data.map { preferences ->
        preferences[EMAIL] ?: ""
    }
    suspend fun saveEmail(email: String) {
        context.userStore.edit { preferences -> preferences[EMAIL] = email }
    }

    //PASSWORD ---------------------------------
    val getPassword: Flow<String?> = context.userStore.data.map { preferences ->
        preferences[PASSWORD] ?: ""
    }
    suspend fun savePassword(password: String) {
        context.userStore.edit { preferences -> preferences[PASSWORD] = password }
    }
}
