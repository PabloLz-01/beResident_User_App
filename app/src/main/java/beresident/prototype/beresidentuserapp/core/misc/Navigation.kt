package beresident.prototype.beresidentuserapp.core.misc

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import beresident.prototype.beresidentuserapp.screens.forgot.ForgotScreen
import beresident.prototype.beresidentuserapp.screens.register.RegisterScreen
import beresident.prototype.beresidentuserapp.screens.login.LoginScreen
import beresident.prototype.beresidentuserapp.screens.settings.SettingsScreen
import beresident.prototype.beresidentuserapp.screens.splash.SplashScreen
import beresident.prototype.beresidentuserapp.screens.splash.widgets.NoInternetScreen

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route
    ){
        composable(route = Screen.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(route = Screen.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(route = Screen.ForgotScreen.route, arguments = listOf()){
            ForgotScreen(navController)
        }
        composable(route = Screen.RegisterScreen.route, arguments = listOf()){
            RegisterScreen(navController)
        }
        composable(route = Screen.MainScreen.route, arguments = listOf()){

        }
        composable(route = Screen.SettingsScreen.route, arguments = listOf()){
            SettingsScreen(navController)
        }
        composable(route = Screen.NoInternet.route, arguments = listOf()){
            NoInternetScreen(navController)
        }
    }
}
