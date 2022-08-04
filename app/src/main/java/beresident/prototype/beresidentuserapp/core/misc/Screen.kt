package beresident.prototype.beresidentuserapp.core.misc


sealed class Screen(val route: String) {

    object SplashScreen: Screen("splash_screen")
    object MainScreen: Screen("main_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object ForgotScreen: Screen("forgot_screen")
    object SettingsScreen: Screen("settings_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

