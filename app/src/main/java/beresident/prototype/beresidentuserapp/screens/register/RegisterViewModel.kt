package beresident.prototype.beresidentuserapp.screens.register

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
class RegisterViewModel @Inject constructor(private val authentication: Authentication): ViewModel() {
    val progress = mutableStateOf(false)
    val snackStatus = mutableStateOf(false)
    val snackMessage = mutableStateOf("")

    fun onCreate(
        name: String,
        lastName: String,
        phone: String,
        email: String,
        password: String,
        snackHostState: SnackbarHostState,
        navController: NavController
    ){
        snackStatus.value = false
        viewModelScope.launch {
            progress.value = true
            val result: Any = authentication.register(name, lastName, phone, email, password)
            progress.value = false
            handler(result as Int, navController)
        }
    }

    private fun handler(
        statusCode: Int,
        navController: NavController
    ): String {
        when (statusCode) {
            401, 422, 403 -> {
                snackStatus.value = true
                snackMessage.value = "Por favor verifique los datos"
            }
            500 -> {
                snackStatus.value = true
                snackMessage.value = "Error al conectarse con la base de datos"
            }
            else -> {
                navController.navigate(Screen.LoginScreen.route){
                    popUpTo(Screen.RegisterScreen.route){ inclusive = true }
                }
            }
        }
        return ""
    }
}