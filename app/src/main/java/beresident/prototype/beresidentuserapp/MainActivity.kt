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
import beresident.prototype.beresidentuserapp.core.misc.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.misc.Navigation
import beresident.prototype.beresidentuserapp.core.misc.StoreTheme
import beresident.prototype.beresidentuserapp.core.services.BiometricService
import beresident.prototype.beresidentuserapp.screens.forgot.ForgotViewModel
import beresident.prototype.beresidentuserapp.screens.login.LoginViewModel
import beresident.prototype.beresidentuserapp.screens.register.RegisterViewModel
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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

    private val focusLiveData = MutableLiveData(false)//verifies if the app was back from foreground
    private val biometricExpiredLiveData = MutableLiveData(false)//verifies if the app was back from foreground
    private val biometricExpiredLiveDataTime = MutableLiveData<Long>(0)//verifies if the app was back from foreground

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        val biometricService = BiometricService(this, this)//Biometric Service
        val biometricStore = BiometricAuthentication(this)//Biometric datastore
        val dataStore = StoreTheme(this)//Theme datastore

        super.onCreate(savedInstanceState)
        setContent {
            val themeValue = dataStore.getTheme.collectAsState(initial = 0.0)//Theme value in datastore
            var theme = isSystemInDarkTheme()//Default value for theme

            val focus = focusLiveData.observeAsState()//Focus as live data
            val isBiometricAuthenticationExpired = biometricExpiredLiveData.observeAsState()

            val scope = rememberCoroutineScope()
            val navController = Navigation()

            //Gets all the values from biometric datastore
            val isBiometricAuthentication = biometricStore.getBiometricAuthentication.collectAsState(initial = false)
            val isAuthenticatedByBiometricAuthentication = biometricStore.getAuthenticatedByBiometricAuthentication.collectAsState(initial = false)
            val biometricAuthenticationTime = biometricStore.getBiometricAuthenticationTime.collectAsState(initial = 0)


            when (themeValue.value) {//Assigns a the theme depending of the themeValue variable
                0 -> theme = isSystemInDarkTheme()
                1 -> theme = false
                2 -> theme = true
            }

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
                println(isBiometricAuthentication.value)
                println(isAuthenticatedByBiometricAuthentication.value)
                println("System  : ${System.currentTimeMillis()}")
                println("DataTime: ${biometricAuthenticationTime.value}")
                println(isBiometricAuthenticationExpired.value)

                scope.launch {
                    if (isBiometricAuthentication.value!!) biometricStore.putAuthenticatedByBiometricAuthentication(false)
                }

                if (isBiometricAuthentication.value!!){ //Verifies if biometric auth is activated
                    if( !isAuthenticatedByBiometricAuthentication.value!! && isBiometricAuthenticationExpired.value!!) {
                        scope.launch{
                            biometricService.requestBiometricAuthentication(biometricStore, scope, navController.navController)
                        }
                    }
                }
            } else {
                biometricExpiredLiveDataTime.value = biometricAuthenticationTime.value!!
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        focusLiveData.value = !focusLiveData.value!!
        biometricExpiredLiveData.value = System.currentTimeMillis() >= biometricExpiredLiveDataTime.value!!
    }
}