package beresident.prototype.beresidentuserapp.screens.forgot

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
class ForgotViewModel @Inject constructor(private val authentication: Authentication): ViewModel(){
    private val error = MutableLiveData<Any>()

    fun onCreate(email: String, snackHostState: SnackbarHostState, navController: NavController){
        viewModelScope.launch {
            val result: Any = authentication.forgot(email)
            error.postValue(handler(result as Int, snackHostState, navController))
        }
    }

    private suspend fun handler(statusCode: Int, snackHostState: SnackbarHostState, navController: NavController): String {
        var message = ""

        when (statusCode) {
            401, 422, 402, 404 -> {
                message = "El correo electronico no se encuentra registrado"
                snackHostState.currentSnackbarData?.dismiss()
                snackHostState.showSnackbar(message)
            }
            500 -> {
                message = "Error al conectarse con la base de datos"
                snackHostState.currentSnackbarData?.dismiss()
                snackHostState.showSnackbar(message)
            }
            else -> {
                navController.navigate(Screen.LoginScreen.route){
                    popUpTo(Screen.ForgotScreen.route){ inclusive = true }
                }
            }
        }

        return message
    }
}