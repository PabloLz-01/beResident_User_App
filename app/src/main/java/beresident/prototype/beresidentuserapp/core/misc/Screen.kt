package beresident.prototype.beresidentuserapp.core.misc

//Defines our navigation routes
sealed class Screen(val route: String) {

    //Routes
    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object ForgotScreen: Screen("forgot_screen")
    object SettingsScreen: Screen("settings_screen")
    object NoInternet: Screen("no-internet-screen")
    object HomeScreen: Screen("home-screen")

    //Routes with arguments
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

