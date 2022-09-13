package beresident.prototype.beresidentuserapp.core.services

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.core.misc.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class BiometricService(val context: Context, val activity: AppCompatActivity, ) : AppCompatActivity(){
    private var canAuth = false

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    fun canUseBiometric() : Boolean{
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
        userCredentials: StoreUserCredentials? = null,
        scope: CoroutineScope,
        navController: NavController,
        attemps: Int,
        lockTime: Int,
        succeedAction: (() -> Unit)? = null,
    ) {
        executor = ContextCompat.getMainExecutor(context)

        biometricPrompt = BiometricPrompt(activity, executor, object: BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                when (errorCode){
                    10 -> {
                        scope.launch{
                            if (attemps == 0){
                                biometricPrompt.cancelAuthentication()
                                userCredentials?.savePassword("")
                                userCredentials?.saveEmail("")
                                if(navController.currentDestination?.route != Screen.LoginScreen.route){
                                    navController.navigate(Screen.LoginScreen.route)
                                }
                            } else {
                                navController.navigate(Screen.HomeScreen.route)
                                biometricStore.putAttemps(attemps-1)
                            }

                        }

                    }
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                scope.launch {
                    biometricStore.putBiometricAuthentication(true)
                    biometricStore.putAuthenticatedByBiometricAuthentication(true)
                    biometricStore.putBiometricAuthenticationTime(lockTime = lockTime)
                    biometricStore.putAttemps(3)
                    if (succeedAction != null) succeedAction()
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                scope.launch{
                    if (attemps == 0 ){
                        biometricPrompt.cancelAuthentication()
                        userCredentials?.saveEmail("")
                        userCredentials?.savePassword("")
                        navController.navigate(Screen.LoginScreen.route)
                    } else {
                        println(attemps)
                        biometricStore.putAttemps(attemps-1)
                        navController.navigate(Screen.HomeScreen.route)
                    }
                }
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autentificacion Biometrica")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
