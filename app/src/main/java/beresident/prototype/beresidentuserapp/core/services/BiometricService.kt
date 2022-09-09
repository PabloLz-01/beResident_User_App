package beresident.prototype.beresidentuserapp.core.services

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.core.misc.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.misc.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class BiometricService(val context: Context, val activity: AppCompatActivity, ) : AppCompatActivity(){
    private var canAuth = false

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    fun setupAuth() : Boolean{
        if (BiometricManager.from(context).canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            ) == BiometricManager.BIOMETRIC_SUCCESS) {
            canAuth = true
        }
        return canAuth
    }

    fun requestBiometricAuthentication(
        biometricStore: BiometricAuthentication,
        scope: CoroutineScope,
        navController: NavController
    ) {
        executor = ContextCompat.getMainExecutor(context)

        biometricPrompt = BiometricPrompt(activity, executor, object: BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                when (errorCode){
                    10 -> {
                        scope.launch{
                            if(navController.currentDestination?.route != Screen.LoginScreen.route){
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        }
                    }
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                scope.launch {
                    if(navController.currentDestination?.route != Screen.HomeScreen.route){
                        //navController.navigate(Screen.HomeScreen.route)
                    }
                    biometricStore.putBiometricAuthentication(true)
                    biometricStore.putBiometricAuthenticationTime()
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                scope.launch{
                    if(navController.currentDestination?.route != Screen.LoginScreen.route){
                        navController.navigate(Screen.LoginScreen.route)
                    }
                    biometricStore.putAuthenticatedByBiometricAuthentication(false)
                    println(navController.currentDestination?.route)
                    println("Failed")
                }
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authenticationaaaaaaaaa")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
