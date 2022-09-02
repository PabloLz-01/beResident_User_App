package beresident.prototype.beresidentuserapp.core.misc

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import beresident.prototype.beresidentuserapp.screens.forgot.ForgotScreen
import beresident.prototype.beresidentuserapp.screens.forgot.ForgotViewModel
import beresident.prototype.beresidentuserapp.screens.home.HomeScreen
import beresident.prototype.beresidentuserapp.screens.register.RegisterScreen
import beresident.prototype.beresidentuserapp.screens.login.LoginScreen
import beresident.prototype.beresidentuserapp.screens.login.LoginViewModel
import beresident.prototype.beresidentuserapp.screens.register.RegisterViewModel
import beresident.prototype.beresidentuserapp.screens.settings.SettingsScreen
import beresident.prototype.beresidentuserapp.screens.splash.SplashScreen
import beresident.prototype.beresidentuserapp.screens.splash.widgets.NoInternetScreen

@Composable
fun Navigation(
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    forgotViewModel: ForgotViewModel,
    authentication: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route
    ){
        composable(route = Screen.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(route = Screen.LoginScreen.route){
            LoginScreen(loginViewModel).Screen(navController, authentication)
        }
        composable(route = Screen.ForgotScreen.route, arguments = listOf()){
            ForgotScreen(forgotViewModel).Screen(navController)
        }
        composable(route = Screen.RegisterScreen.route, arguments = listOf()){
            RegisterScreen(registerViewModel).Screen(navController)
        }
        composable(route = Screen.MainScreen.route, arguments = listOf()){

        }
        composable(route = Screen.SettingsScreen.route, arguments = listOf()){
            SettingsScreen(navController)
        }
        composable(route = Screen.NoInternet.route, arguments = listOf()){
            NoInternetScreen(navController)
        }
        composable(route = Screen.HomeScreen.route, arguments = listOf()){
            HomeScreen().Screen(navController)
        }
    }
}
