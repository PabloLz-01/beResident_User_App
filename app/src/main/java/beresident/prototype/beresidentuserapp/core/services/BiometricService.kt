package beresident.prototype.beresidentuserapp.core.services

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.core.misc.BiometricAuthentication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BiometricService(val context: Context, val activity: AppCompatActivity): AppCompatActivity(){

    private var canAuth = false
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    fun setupAuth(){
        if (BiometricManager.from(context).canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            ) == BiometricManager.BIOMETRIC_SUCCESS) {
            canAuth = true

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autentificacion Biometrica")
                .setSubtitle("Autenticate utilizando el sensor biometrico")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .setNegativeButtonText("asdasdasdas")
                .build()
        }
    }

    fun authenticate(scope: CoroutineScope, ){
        val biometric = BiometricAuthentication(context)

        if (canAuth) {
            BiometricPrompt(activity, ContextCompat.getMainExecutor(context),
                object: BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        scope.launch {
                            biometric.putAuthenticatedByBiometricAuthentication(false)
                        }
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        scope.launch {
                            biometric.putAuthenticatedByBiometricAuthentication(true)
                        }
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)

                        when (errorCode){
                            10 -> { finish() }
                        }
                    }
                }).authenticate(promptInfo)
        }
    }
}
