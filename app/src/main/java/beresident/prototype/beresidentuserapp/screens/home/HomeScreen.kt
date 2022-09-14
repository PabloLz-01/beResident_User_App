package beresident.prototype.beresidentuserapp.screens.home

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material.*
import androidx.navigation.NavController
import androidx.compose.runtime.*
import beresident.prototype.beresidentuserapp.core.services.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.services.StoreUserCredentials
import beresident.prototype.beresidentuserapp.core.services.BiometricService

//Home screen functions
class HomeScreen: AppCompatActivity(){

    @Composable// Home screen view
    fun Screen(navController: NavController, context:Context, activity: AppCompatActivity){
        val userCredentials = StoreUserCredentials(this)
        val biometricService = BiometricService(context, activity) // Biometric service
        val biometricStore = BiometricAuthentication(this) // Biometric data store

        //Gets the values that we store in the data store
        val isAuthenticatedByBiometric = biometricStore.getAuthenticatedByBiometricAuthentication.collectAsState(initial = false)
        val isBiometricAuthentication = biometricStore.getBiometricAuthentication.collectAsState(initial = false)
        val biometricTime = biometricStore.getBiometricAuthenticationTime.collectAsState(initial = 0)
        val attempts = biometricStore.getAttemps.collectAsState(initial = 0)
        val lockTime = biometricStore.getLockTime.collectAsState(initial = 0)
        var biometricExpired: Boolean
        val scope = rememberCoroutineScope()

        // If biometric is activated and the user authenticated is false, we request biometrics
        LaunchedEffect(key1 = true){
            biometricExpired = System.currentTimeMillis() >= biometricTime.value!!
            println(isAuthenticatedByBiometric.value)
            println(isBiometricAuthentication.value)
            println(biometricExpired)
            println(System.currentTimeMillis())
            println(biometricTime.value)
            if (!isAuthenticatedByBiometric.value!! && isBiometricAuthentication.value!! && biometricExpired){
                print("aaaa")
                biometricService.requestBiometricAuthentication(
                    biometricStore,
                    userCredentials ,
                    scope,
                    navController,
                    attempts.value!!,
                    lockTime.value
                )
            }
        }


        // Main view
        Scaffold{
            Column{
                AndroidView(factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                        loadUrl("https://app.beresident.mx/") }
                    }, update = {it.loadUrl("https://app.beresident.mx/")})
            }
        }
    }
}
