package beresident.prototype.beresidentuserapp.core.misc

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import beresident.prototype.beresidentuserapp.screens.forgot.ForgotScreen
import beresident.prototype.beresidentuserapp.screens.register.RegisterScreen
import beresident.prototype.beresidentuserapp.screens.login.LoginScreen
import beresident.prototype.beresidentuserapp.screens.settings.SettingsScreen
import beresident.prototype.beresidentuserapp.screens.splash.SplashScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){
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
    }
}
