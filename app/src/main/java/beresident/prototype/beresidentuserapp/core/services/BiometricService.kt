package beresident.prototype.beresidentuserapp.core.services

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.core.misc.Screen
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

//Class that is in charge of biometric behaviour
class BiometricService(val context: Context, val activity: AppCompatActivity) : AppCompatActivity(){
    //Var that let us know if the user can use biometric auth
    private var canAuth = false

    //Biometric components
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    //Fun that let us know if the user can use biometric auth
    fun canUseBiometric() : Boolean{
        if (BiometricManager.from(context).canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    or BiometricManager.Authenticators.BIOMETRIC_WEAK
            ) == BiometricManager.BIOMETRIC_SUCCESS) {
            canAuth = true
        }
        return canAuth
    }

    //Fun that requests biometric auth
    fun requestBiometricAuthentication(
        biometricStore: BiometricAuthentication,
        userCredentials: StoreUserCredentials? = null,
        scope: CoroutineScope,
        navController: NavController,
        attempts: Int,
        lockTime: Int,
        succeedAction: (() -> Unit)? = null,
    ) {
        executor = ContextCompat.getMainExecutor(context)//Context

        //Biometric callbacks in all the cases listed below
        biometricPrompt = BiometricPrompt(activity, executor, object: BiometricPrompt.AuthenticationCallback() {

            //AUTH FAILED -------------------
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                when (errorCode){
                    10 -> {
                        scope.launch{
                            if (attempts == 0){
                                biometricPrompt.cancelAuthentication()
                                userCredentials?.savePassword("")
                                userCredentials?.saveEmail("")
                                if(navController.currentDestination?.route != Screen.LoginScreen.route){
                                    navController.navigate(Screen.LoginScreen.route)
                                }
                            } else {
                                navController.navigate(Screen.HomeScreen.route){
                                    popUpTo(Screen.LoginScreen.route){ inclusive = true }
                                }
                                biometricStore.putAttempts(attempts-1)
                            }

                        }

                    }
                }
            }

            //AUTH SUCCEEDED --------------------------------
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                scope.launch {
                    biometricStore.putBiometricAuthentication(true)
                    biometricStore.putAuthenticatedByBiometricAuthentication(true)
                    biometricStore.putBiometricAuthenticationTime(lockTime = lockTime)
                    biometricStore.putAttempts(3)
                    if (succeedAction != null) succeedAction()
                }
            }

            //AUTH FAILED -------------------------------------
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                scope.launch{
                    if (attempts == 0 ){
                        biometricPrompt.cancelAuthentication()
                        userCredentials?.saveEmail("")
                        userCredentials?.savePassword("")
                        navController.navigate(Screen.LoginScreen.route)
                    } else {
                        println(attempts)
                        biometricStore.putAttempts(attempts-1)
                        navController.navigate(Screen.HomeScreen.route){
                            popUpTo(Screen.HomeScreen.route){ inclusive = true }
                        }
                    }
                }
            }
        })

        //Sets our biometric prompt information
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autentificacion Biometrica")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        //Launches our biometric prompt
        biometricPrompt.authenticate(promptInfo)
    }
}
