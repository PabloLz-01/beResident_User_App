package beresident.prototype.beresidentuserapp.screens.forgot

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.core.model.Authentication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel //Forgot view model calls to the API
class ForgotViewModel @Inject constructor(private val authentication: Authentication): ViewModel(){
    val progress = mutableStateOf(false) //Progress of the call
    val snackStatus = mutableStateOf(false) //Status of the snack bar
    val snackMessage = mutableStateOf("") //Snack bar message

    // Call our Forgot API sending the correct parameters
    fun onCreate(email: String, navController: NavController){
        snackStatus.value = false
        viewModelScope.launch {
            progress.value = true
            val result: Any = authentication.forgot(email)
            progress.value = false
            handler(result as Int, navController)
        }
    }

    //Function that handles any errors may appear in the api callback
    private fun handler(statusCode: Int, navController: NavController){
        when (statusCode) {
            //Backend error
            401, 422, 402, 404 -> {
                snackStatus.value = true
                snackMessage.value = "El correo electronico no se encuentra registrado"
            }
            //Server error
            500 -> {
                snackStatus.value = true
                snackMessage.value = "Error al conectarse con la base de datos"
            }
            //Succeed call
            else -> {
                navController.navigate(Screen.LoginScreen.route){
                    popUpTo(Screen.ForgotScreen.route){ inclusive = true }
                }
            }
        }
    }
}