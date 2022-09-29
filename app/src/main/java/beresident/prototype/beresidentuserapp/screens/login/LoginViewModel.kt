package beresident.prototype.beresidentuserapp.screens.login

import android.content.Context
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.core.services.StoreUserCredentials
import beresident.prototype.beresidentuserapp.core.model.Authentication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authentication: Authentication) : ViewModel(){
    val progress = mutableStateOf(false)
    val snackStatus = mutableStateOf(false)
    val snackMessage = mutableStateOf("")

    fun onCreate(
        email: String,
        password: String,
        check: Boolean,
        context: Context,
        navController: NavController,
    ){
        snackStatus.value = false
        viewModelScope.launch {
            progress.value = true
            val result: Any = authentication.invoke(email, password)
            progress.value = false
            print(result)
            handler(
                result as Int,
                email,
                password,
                context,
                check,
                navController,
            )
        }

    }

    private suspend fun handler(
        statusCode: Int,
        email: String,
        password: String,
        context: Context,
        check: Boolean,
        navController: NavController,
    ): String{
        when (statusCode) {
            401, 422, 403 -> {
                snackStatus.value = true
                snackMessage.value = "El usuario no existe"
            }
            500 -> {
                snackStatus.value = true
                snackMessage.value = "Error al conectarse con la base de datos"
            }
            else -> {
                if (check) rememberLogin(email, password, context)
                navController.navigate(Screen.HomeScreen.route){
                    popUpTo(Screen.LoginScreen.route){ inclusive = true }
                }
            }
        }
        return ""
    }

    private suspend fun rememberLogin(email: String, password: String, context: Context){
        val dataStore = StoreUserCredentials(context)
        dataStore.saveEmail(email)
        dataStore.savePassword(password)
    }
}