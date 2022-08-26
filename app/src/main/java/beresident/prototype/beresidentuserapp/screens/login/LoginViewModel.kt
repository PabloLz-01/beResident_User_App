package beresident.prototype.beresidentuserapp.screens.login

import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.core.model.Authentication
import beresident.prototype.beresidentuserapp.screens.shared.showSnackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authentication: Authentication) : ViewModel(){
    val error = MutableLiveData<Any>()

    fun onCreate(
        email: String,
        password: String,
        snackbarHostState: SnackbarHostState,
        navController: NavController
    ){
        viewModelScope.launch {
            val result: Any = authentication.invoke(email, password)
            error.postValue(handler(result as Int, snackbarHostState, navController))
        }
    }

    private suspend fun handler(
        statusCode: Int,
        snackbarHostState: SnackbarHostState,
        navController: NavController
    ): String{
        var message = ""

        when (statusCode) {
            401, 422, 403 -> {
                message = "El usuario no existe"
                snackbarHostState.showSnackbar(message)
            }
            else -> {
                navController.navigate(Screen.MainScreen.route){
                    popUpTo(Screen.LoginScreen.route){ inclusive = true }
                }
            }
        }
        return message
    }
}