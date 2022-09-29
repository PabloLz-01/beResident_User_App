package beresident.prototype.beresidentuserapp.core.misc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import beresident.prototype.beresidentuserapp.screens.forgot.ForgotScreen
import beresident.prototype.beresidentuserapp.screens.forgot.ForgotViewModel
import beresident.prototype.beresidentuserapp.screens.home.HomeScreen
import beresident.prototype.beresidentuserapp.screens.register.RegisterScreen
import beresident.prototype.beresidentuserapp.screens.login.LoginScreen
import beresident.prototype.beresidentuserapp.screens.login.LoginViewModel
import beresident.prototype.beresidentuserapp.screens.register.RegisterViewModel
import beresident.prototype.beresidentuserapp.screens.settings.SettingsScreen
import beresident.prototype.beresidentuserapp.screens.splash.SplashScreen
import beresident.prototype.beresidentuserapp.screens.splash.NoInternetScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi

//Class that manages our navigation system
class Navigation{
    // Sets time and easing values
    private val tweenSpec = tween<IntOffset>(durationMillis = 1, easing = CubicBezierEasing(0.0f,1.0f,0.0f,1.0f))
    // Controller that can be instance in other classes
    lateinit var  navController: NavHostController

    @OptIn(ExperimentalAnimationApi::class, ExperimentalCoroutinesApi::class)
    @Composable
    fun Navigate( //Declares our navigation routes and animations
        loginViewModel: LoginViewModel,
        registerViewModel: RegisterViewModel,
        forgotViewModel: ForgotViewModel,
        activity: AppCompatActivity,
        context: Context,
    ) {
        //Saves the routes of all our screens
        navController = rememberAnimatedNavController()

        //Component that requires of a controller and a start destination
        AnimatedNavHost(navController = navController, startDestination = Screen.SplashScreen.route
        ){
            //SPLASH SCREEN --------------------------------
            composable(route = Screen.SplashScreen.route){
                SplashScreen(navController)
            }
            //LOGIN SCREEN ---------------------------------
            composable(
                route = Screen.LoginScreen.route,
                enterTransition = {slideInVertically(tweenSpec){0} },
                popEnterTransition = { slideInVertically(tweenSpec){0} }
            ){
                LoginScreen(loginViewModel, activity, context).Screen(navController)
            }
            //FORGOT SCREEN --------------------------------
            composable(
                route = Screen.ForgotScreen.route,
                enterTransition = { slideInHorizontally(tweenSpec) {0}},
                exitTransition = { slideOutVertically(tweenSpec){0}},
                popExitTransition = {slideOutVertically(tweenSpec){0}}
            ){
                ForgotScreen(forgotViewModel).Screen(navController)
            }
            //REGISTER SCREEN ---------------------------------------
            composable(
                route = Screen.RegisterScreen.route,
                enterTransition = { slideInVertically(tweenSpec){0}},
                exitTransition = { slideOutVertically(tweenSpec){0}},
                popExitTransition = {slideOutVertically(tweenSpec){0}}
            ){
                RegisterScreen(registerViewModel).Screen(navController)
            }
            //SETTINGS SCREEN -------------------------------------
            composable(
                route = Screen.SettingsScreen.route,
                enterTransition = { slideInVertically(tweenSpec){0}},
                exitTransition = { slideOutVertically(tweenSpec){0}},
                popExitTransition = {slideOutVertically(tweenSpec){0}}
            ){
                SettingsScreen(activity).Screen(navController)
            }
            //NO INTERNET SCREEN -----------------------------------
            composable(route = Screen.NoInternet.route){
                NoInternetScreen(navController)
            }
            //HOME SCREEN ------------------------------------------
            composable(route = Screen.HomeScreen.route){
                HomeScreen().Screen(navController, context, activity)
            }
        }
    }
}


