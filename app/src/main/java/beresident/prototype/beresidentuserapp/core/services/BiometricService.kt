package beresident.prototype.beresidentuserapp.core.services

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import beresident.prototype.beresidentuserapp.core.misc.BiometricAuthentication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class BiometricService(val context: Context, val activity: AppCompatActivity): AppCompatActivity(){

    private var canAuth = false

    fun setupAuth() : Boolean{
        if (BiometricManager.from(context).canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            ) == BiometricManager.BIOMETRIC_SUCCESS) {
            canAuth = true
        }
        return canAuth
    }

    /*fun authenticate(
        succeeded: () -> Unit,
        failed: () -> Unit,
        error: () -> Unit,
        scope: CoroutineScope? = null
    ){
        val biometricStore = BiometricAuthentication(context)

        if (canAuth) {
            BiometricPrompt(activity, ContextCompat.getMainExecutor(context),
                object: BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        failed()
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        scope?.launch {
                            biometricStore.putAuthenticatedByBiometricAuthentication(true)
                        }

                        succeeded()

                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        error()
                    }
                }
            ).authenticate(promptInfo)
        }
    }*/
}
