package beresident.prototype.beresidentuserapp.core.misc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
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
import beresident.prototype.beresidentuserapp.screens.splash.widgets.NoInternetScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


class Navigation{
    lateinit var  navController: NavHostController

    val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)
    val tweenSpec = tween<IntOffset>(durationMillis = 1, easing = CubicBezierEasing(0.0f,1.0f,0.0f,1.0f))


    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun Navigate(
        loginViewModel: LoginViewModel,
        registerViewModel: RegisterViewModel,
        forgotViewModel: ForgotViewModel,
        activity: AppCompatActivity,
        context: Context,
    ) {
        navController = rememberAnimatedNavController()
        val configuration = LocalConfiguration.current

        AnimatedNavHost(navController = navController, startDestination = Screen.SplashScreen.route
        ){
            composable(route = Screen.SplashScreen.route){
                SplashScreen(navController)
            }
            composable(
                route = Screen.LoginScreen.route,
                enterTransition = {slideInVertically(tweenSpec){0} },
                popEnterTransition = { slideInVertically(tweenSpec){0} }
            ){
                LoginScreen(loginViewModel, activity, context).Screen(navController)
            }
            composable(
                route = Screen.ForgotScreen.route,
                enterTransition = { slideInHorizontally(tweenSpec) {0}},
                exitTransition = { slideOutVertically(tweenSpec){0}},
                popExitTransition = {slideOutVertically(tweenSpec){0}}
            ){
                ForgotScreen(forgotViewModel).Screen(navController)
            }
            composable(
                route = Screen.RegisterScreen.route,
                enterTransition = { slideInVertically(tweenSpec){0}},
                exitTransition = { slideOutVertically(tweenSpec){0}},
                popExitTransition = {slideOutVertically(tweenSpec){0}}
            ){
                RegisterScreen(registerViewModel).Screen(navController)
            }
            composable(route = Screen.MainScreen.route){

            }
            composable(
                route = Screen.SettingsScreen.route,
                enterTransition = { slideInVertically(tweenSpec){0}},
                exitTransition = { slideOutVertically(tweenSpec){0}},
                popExitTransition = {slideOutVertically(tweenSpec){0}}
            ){
                SettingsScreen(activity).Screen(navController)
            }
            composable(route = Screen.NoInternet.route){
                NoInternetScreen(navController)
            }
            composable(route = Screen.HomeScreen.route){
                HomeScreen().Screen(navController, context, activity)
            }
        }
    }
}


