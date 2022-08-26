package beresident.prototype.beresidentuserapp.screens.register

import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.core.model.Authentication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authentication: Authentication): ViewModel() {
    val error = MutableLiveData<Any>()

    fun onCreate(
        name: String,
        lastName: String,
        phone: String,
        email: String,
        password: String,
        snackbarHostState: SnackbarHostState,
        navController: NavController
    ){
        viewModelScope.launch {
            val result: Any = authentication.register(name, lastName, phone, email, password)
            error.postValue(handler(result as Int, snackbarHostState, navController))
        }
    }

    private suspend fun handler(
        statusCode: Int,
        snackbarHostState: SnackbarHostState,
        navController: NavController
    ): String {
        var message = ""

        when (statusCode) {
            401, 422, 403 -> {
                message = "Por favor verifique los datos"
                snackbarHostState.showSnackbar(message)
            }
            else -> {
                navController.navigate(Screen.LoginScreen.route){
                    popUpTo(Screen.RegisterScreen.route){ inclusive = true }
                }
            }
        }
        return message
    }
}