package beresident.prototype.beresidentuserapp.core.services

import android.annotation.SuppressLint
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

    //APP THEME ----------------------------------------
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

class BiometricAuthentication(private val context: Context){
    //SAVE USER BIOMETRICS AND CONFIGURATION ----------------------
    companion object {
        private val Context.biometricStore: DataStore<Preferences> by preferencesDataStore("biometric")
        val BIOMETRIC_AUTHENTICATION = booleanPreferencesKey("biometric_authentication")
        val BIOMETRIC_AUTHENTICATION_TIME = longPreferencesKey("biometric_authentication_time")
        val AUTHENTICATED_BY_BIOMETRIC_AUTHENTICATION = booleanPreferencesKey("authenticated_by_biometric_authentication")
        val LOCK_TIME = intPreferencesKey("lock_time")
        val ATTEMPTS = intPreferencesKey("attempts")
    }

    //BIOMETRIC AUTHENTICATION ------------------------------
    suspend fun putBiometricAuthentication(value: Boolean){
        context.biometricStore.edit { preferences ->
            preferences[BIOMETRIC_AUTHENTICATION] = value
        }
    }

    val getBiometricAuthentication: Flow<Boolean?> = context.biometricStore.data.map{ preferences ->
        preferences[BIOMETRIC_AUTHENTICATION] ?: false
    }

    //BIOMETRIC AUTHENTICATION TIME ------------------------
    @SuppressLint("CoroutineCreationDuringComposition")
    suspend fun putBiometricAuthenticationTime(reset: Boolean = false, lockTime: Int){
        println(lockTime)
        val time: Long =
            if (reset) {
                System.currentTimeMillis()
            } else{
                System.currentTimeMillis() + (lockTime * 60000)
            }

        context.biometricStore.edit { preferences ->
            preferences[BIOMETRIC_AUTHENTICATION_TIME] = time
        }
    }

    val getBiometricAuthenticationTime: Flow<Long?> = context.biometricStore.data.map { preferences ->
        preferences[BIOMETRIC_AUTHENTICATION_TIME] ?: System.currentTimeMillis()
    }

    //AUTHENTICATED BY BIOMETRIC AUTHENTICATION ---------
    suspend fun putAuthenticatedByBiometricAuthentication(value: Boolean){
        context.biometricStore.edit { preferences ->
            preferences[AUTHENTICATED_BY_BIOMETRIC_AUTHENTICATION] = value
        }
    }

    val getAuthenticatedByBiometricAuthentication: Flow<Boolean?> = context.biometricStore.data.map { preferences ->
        preferences[AUTHENTICATED_BY_BIOMETRIC_AUTHENTICATION] ?: false
    }

    //LOCK TIME ------------------------------------------
    suspend fun putLockTime(value: Int, reset: Boolean){
        context.biometricStore.edit { preferences -> preferences[LOCK_TIME] = value }
        putBiometricAuthentication(reset)
    }

    val getLockTime: Flow<Int> = context.biometricStore.data.map { preferences ->
        preferences[LOCK_TIME] ?: 0
    }

    //BIOMETRIC ATTEMPTS -----------------------------------------
    val getAttempts: Flow<Int?> = context.biometricStore.data.map { preferences ->
        preferences[ATTEMPTS] ?: 0
    }
    suspend fun putAttempts(attempts: Int) {
        context.biometricStore.edit { preferences -> preferences[ATTEMPTS] = attempts }
    }
}