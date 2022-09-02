package beresident.prototype.beresidentuserapp.screens.home

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material.*
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.screens.shared.CustomTopBar
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import beresident.prototype.beresidentuserapp.core.misc.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.misc.Screen
import kotlinx.coroutines.delay

class HomeScreen(): AppCompatActivity(){

    @Composable
    fun Screen(navController: NavController){
        val biometricStore = BiometricAuthentication(LocalContext.current)
        val authenticatedByBiometricAuthentication = biometricStore
            .getAuthenticatedByBiometricAuthentication.collectAsState(initial = false)

        if (!authenticatedByBiometricAuthentication.value!!) {
            navController.navigate(Screen.LoginScreen.route)
        }
        Scaffold() {
            Column() {
                CustomTopBar(text = "Home Screen", action = {})
                AndroidView(factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                        loadUrl("https://www.geeksforgeeks.org") }
                    }, update = {it.loadUrl("https://www.geeksforgeeks.org")})
            }
        }
    }
}
