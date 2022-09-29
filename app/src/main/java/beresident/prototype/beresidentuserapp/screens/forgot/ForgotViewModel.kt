package beresident.prototype.beresidentuserapp.screens.forgot

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
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
    val progress = mutableStateOf(false)
    val snackStatus = mutableStateOf(false)
    val snackMessage = mutableStateOf("")

    fun onCreate(email: String, snackHostState: SnackbarHostState, navController: NavController){
        snackStatus.value = false
        viewModelScope.launch {
            progress.value = true
            val result: Any = authentication.forgot(email)
            progress.value = false
            handler(result as Int, snackHostState, navController)
        }
    }

    private suspend fun handler(statusCode: Int, snackHostState: SnackbarHostState, navController: NavController): String {

        when (statusCode) {
            401, 422, 402, 404 -> {
                snackStatus.value = true
                snackMessage.value = "El correo electronico no se encuentra registrado"
            }
            500 -> {
                snackStatus.value = true
                snackMessage.value = "Error al conectarse con la base de datos"
            }
            else -> {
                navController.navigate(Screen.LoginScreen.route){
                    popUpTo(Screen.ForgotScreen.route){ inclusive = true }
                }
            }
        }
        return ""
    }
}