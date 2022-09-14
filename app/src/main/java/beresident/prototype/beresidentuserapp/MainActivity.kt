package beresident.prototype.beresidentuserapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData
import beresident.prototype.beresidentuserapp.core.services.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.misc.Navigation
import beresident.prototype.beresidentuserapp.core.services.StoreTheme
import beresident.prototype.beresidentuserapp.core.services.StoreUserCredentials
import beresident.prototype.beresidentuserapp.core.services.BiometricService
import beresident.prototype.beresidentuserapp.screens.forgot.ForgotViewModel
import beresident.prototype.beresidentuserapp.screens.login.LoginViewModel
import beresident.prototype.beresidentuserapp.screens.register.RegisterViewModel
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    //ViewModels-------------------------------------------------------
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val forgotViewModel: ForgotViewModel by viewModels()

    //MainActivity context & activity
    private val activity = this
    private val context = this

    //Live data that shots when app comes back from foreground
    private val focusLiveData = MutableLiveData(true)//verifies if the app was back from foreground
    private val biometricExpiredLiveData = MutableLiveData(true)//verifies if the app was back from foreground
    private val biometricExpiredLiveDataTime = MutableLiveData<Long>(0)//verifies if the app was back from foreground

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        val biometricService = BiometricService(this, this)//Biometric Service
        val biometricStore = BiometricAuthentication(this)//Biometric datastore
        val dataStore = StoreTheme(this)//Theme datastore

        super.onCreate(savedInstanceState)
        setContent {
            val userCredentials = StoreUserCredentials(this)
            val themeValue = dataStore.getTheme.collectAsState(initial = 0.0)//Theme value in datastore
            var theme = isSystemInDarkTheme()//Default value for theme

            val focus = focusLiveData.observeAsState()//Focus as live data
            val isBiometricAuthenticationExpired = biometricExpiredLiveData.observeAsState()//Verifies is biometric time is expired

            val scope = rememberCoroutineScope()//Scope
            val navController = Navigation()//Navigation controller

            //Gets all the values from biometric datastore
            val isBiometricAuthentication = biometricStore.getBiometricAuthentication.collectAsState(initial = false)
            val isAuthenticatedByBiometricAuthentication = biometricStore.getAuthenticatedByBiometricAuthentication.collectAsState(initial = false)
            val biometricAuthenticationTime = biometricStore.getBiometricAuthenticationTime.collectAsState(initial = 0)
            val attempts = biometricStore.getAttemps.collectAsState(initial = 0)
            val lockTime = biometricStore.getLockTime.collectAsState(initial = 0)

            val userEmail = userCredentials.getEmail.collectAsState(initial = "")
            val userPassword = userCredentials.getPassword.collectAsState(initial = "")

            when (themeValue.value) {//Assigns a the theme depending of the themeValue variable
                0 -> theme = isSystemInDarkTheme() //System default
                1 -> theme = false // Light theme
                2 -> theme = true // Dark theme
            }

            //Main View
            DefaultTheme(darkTheme = theme) {
                navController.Navigate(
                    loginViewModel,
                    registerViewModel,
                    forgotViewModel,
                    activity,
                    context,
                )
            }

            if (focus.value == true) {//Verifies if app was on foreground
                println(isAuthenticatedByBiometricAuthentication.value)
                println(isBiometricAuthentication.value)
                println(isBiometricAuthenticationExpired.value)

                //Detects if biometric auth time is expired
                biometricExpiredLiveData.value = System.currentTimeMillis() >= biometricExpiredLiveDataTime.value!!

                biometricExpiredLiveDataTime.value = biometricAuthenticationTime.value!! //Gets time save in store
                focusLiveData.value = false // Resets focus state


                scope.launch {
                    //If time expired, then our authenticatedByBiometric is false
                    if (isBiometricAuthentication.value!!) biometricStore.putAuthenticatedByBiometricAuthentication(false)
                }
                if (userEmail.value != "" && userPassword.value != ""){
                    if (isBiometricAuthentication.value!!){ //Verifies if biometric auth is activated
                        if( !isAuthenticatedByBiometricAuthentication.value!! && isBiometricAuthenticationExpired.value!!) {
                            scope.launch{
                                biometricService.requestBiometricAuthentication(
                                    biometricStore,
                                    userCredentials,
                                    scope,
                                    navController.navController,
                                    attempts.value!!,
                                    lockTime.value
                                )
                            }
                        }
                    }
                }

            } else {
                biometricExpiredLiveData.value = System.currentTimeMillis() >= biometricExpiredLiveDataTime.value!!

                biometricExpiredLiveDataTime.value = biometricAuthenticationTime.value!! //Gets time save in store
                focusLiveData.value = false // Resets focus state


                scope.launch {
                    //If time expired, then our authenticatedByBiometric is false
                    if (isBiometricAuthentication.value!!) biometricStore.putAuthenticatedByBiometricAuthentication(false)
                }
            }
        }
    }

    //Detects when app comes from foreground
    override fun onStart() {
        super.onStart()

        focusLiveData.value = true //Starts our focus state
        //Detects if biometric auth time is expired
        biometricExpiredLiveData.value = System.currentTimeMillis() >= biometricExpiredLiveDataTime.value!!
    }
}