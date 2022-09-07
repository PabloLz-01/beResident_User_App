package beresident.prototype.beresidentuserapp.screens.home

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material.*
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import beresident.prototype.beresidentuserapp.core.misc.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.services.BiometricService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class HomeScreen: AppCompatActivity(){


    @Composable
    fun Screen(navController: NavController, context:Context, activity: AppCompatActivity){
        val biometricService = BiometricService(context, activity)

        val biometricStore = BiometricAuthentication(LocalContext.current)
        val getBiometricAuthentication = biometricStore
            .getBiometricAuthentication.collectAsState(initial = false)

        LaunchedEffect(true){
            biometricService.setupAuth()
            if (getBiometricAuthentication.value!!) {
                biometricService.authenticate(
                    succeeded = { println("succeed")},
                    failed = { println("failed")},
                    error = { println("errpr")}
                )
            }
        }


        Scaffold() {
            Column() {
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
