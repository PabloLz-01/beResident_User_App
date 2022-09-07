package beresident.prototype.beresidentuserapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.content.ContextCompat
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
import java.util.concurrent.Executor

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    //ViewModels-------------------------------------------------------
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val forgotViewModel: ForgotViewModel by viewModels()

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    //MainActivity context & activity
    private val activity = this
    private val context = this

    private val focusLiveData = MutableLiveData(false)//verifies if the app was back from foreground

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        val biometricStore = BiometricAuthentication(this)//Biometric datastore
        val dataStore = StoreTheme(this)//Theme datastore


        super.onCreate(savedInstanceState)
        setContent {
            val themeValue = dataStore.getTheme.collectAsState(initial = 0)//Theme value in datastore
            var theme = isSystemInDarkTheme()//Default value for theme
            val focus = focusLiveData.observeAsState()//Focus as live data

            val scope = rememberCoroutineScope()

            when (themeValue.value) {//Assigns a the theme depending of the themeValue variable
                0 -> theme = isSystemInDarkTheme()
                1 -> theme = false
                2 -> theme = true
            }

            DefaultTheme(darkTheme = theme) {
                Navigation(
                    loginViewModel,
                    registerViewModel,
                    forgotViewModel,
                    activity,
                    context
                )
            }


            if (focus.value == false) {//Verifies if app was on foreground
                //Gets all the values from biometric datastore
                val isBiometricAuthentication = biometricStore.getBiometricAuthentication.collectAsState(initial = false)
                val isAuthenticatedByBiometricAuthentication = biometricStore.getAuthenticatedByBiometricAuthentication.collectAsState(initial = false)
                val biometricAuthenticationTime = biometricStore.getBiometricAuthenticationTime.collectAsState(initial = 0)

                //Sends a true or false if time is equal or bigger than biometric auth time
                val isBiometricAuthenticationExpired = System.currentTimeMillis() >= biometricAuthenticationTime.value!!

                val biometricService = BiometricService(context, activity)//Initializes biometric services
                biometricService.setupAuth()//Setups the biometric service

                println("Biometric auth: ${isBiometricAuthentication.value}")
                println("Is authenticated by biometric: ${isAuthenticatedByBiometricAuthentication.value}")
                println("Is biometric expired:${isBiometricAuthenticationExpired}")

                if (isBiometricAuthentication.value!!){ //Verifies if biometric auth is activated
                    if(isAuthenticatedByBiometricAuthentication.value!! && isBiometricAuthenticationExpired) {
                        //Sends the biometric window if biometric auth succeed and if the
                        //biometric time has expired
                        biometricService.authenticate(
                            succeeded = { println("succeed")}, failed = { println("failed") }, error = { println("error")}, scope)
                    }

                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        focusLiveData.value = !focusLiveData.value!!
    }

    private  fun requestBiometric(){
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor, object: BiometricPrompt.AuthenticationCallback() {

        })
    }
}