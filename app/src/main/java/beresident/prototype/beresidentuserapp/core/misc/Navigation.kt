package beresident.prototype.beresidentuserapp.core.misc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import beresident.prototype.beresidentuserapp.core.services.BiometricService
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

class Navigation(){
    lateinit var  navController: NavHostController

    @Composable
    fun Navigate(
        loginViewModel: LoginViewModel,
        registerViewModel: RegisterViewModel,
        forgotViewModel: ForgotViewModel,
        activity: AppCompatActivity,
        context: Context,
    ) {
        navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.SplashScreen.route
        ){
            composable(route = Screen.SplashScreen.route){
                SplashScreen(navController)
            }
            composable(route = Screen.LoginScreen.route){
                LoginScreen(loginViewModel, activity, context).Screen(navController)
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
                SettingsScreen(activity).Screen(navController)
            }
            composable(route = Screen.NoInternet.route, arguments = listOf()){
                NoInternetScreen(navController)
            }
            composable(route = Screen.HomeScreen.route, arguments = listOf()){
                HomeScreen().Screen(navController, context, activity)
            }
        }
    }
}


