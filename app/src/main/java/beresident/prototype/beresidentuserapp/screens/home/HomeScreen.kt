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
import androidx.compose.ui.platform.LocalContext
import beresident.prototype.beresidentuserapp.core.misc.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.services.BiometricService

class HomeScreen: AppCompatActivity(){

    @Composable
    fun Screen(navController: NavController, context:Context, activity: AppCompatActivity){

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
