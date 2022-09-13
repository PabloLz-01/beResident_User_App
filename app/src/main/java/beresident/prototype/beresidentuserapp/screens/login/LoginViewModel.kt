package beresident.prototype.beresidentuserapp.screens.login

import android.content.Context
import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.MutableLiveData
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
    private val error = MutableLiveData<Any>()

    fun onCreate(
        email: String,
        password: String,
        check: Boolean,
        context: Context,
        snackHostState: SnackbarHostState,
        navController: NavController,
    ){
        viewModelScope.launch {
            val result: Any = authentication.invoke(email, password)
            error.postValue(handler(
                result as Int,
                email,
                password,
                context,
                check,
                snackHostState,
                navController,
            ))
        }
    }

    private suspend fun handler(
        statusCode: Int,
        email: String,
        password: String,
        context: Context,
        check: Boolean,
        snackHostState: SnackbarHostState,
        navController: NavController,
    ): String{
        var message = ""

        when (statusCode) {
            401, 422, 403 -> {
                message = "El usuario no existe"
                snackHostState.currentSnackbarData?.dismiss()
                snackHostState.showSnackbar(message)
            }
            500 -> {
                message = "Error al conectarse con la base de datos"
                snackHostState.currentSnackbarData?.dismiss()
                snackHostState.showSnackbar(message)
            }
            else -> {
                if (check) rememberLogin(email, password, context)

                navController.navigate(Screen.HomeScreen.route){
                    popUpTo(Screen.LoginScreen.route){ inclusive = true }
                }
            }
        }
        return message
    }

    private suspend fun rememberLogin(email: String, password: String, context: Context){
        val dataStore = StoreUserCredentials(context)
        dataStore.saveEmail(email)
        dataStore.savePassword(password)
    }
}