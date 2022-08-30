package beresident.prototype.beresidentuserapp.screens.home

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.screens.shared.CustomTopBar

class HomeScreen {
    @Composable
    fun Screen(navController: NavController){
        Scaffold (){
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
