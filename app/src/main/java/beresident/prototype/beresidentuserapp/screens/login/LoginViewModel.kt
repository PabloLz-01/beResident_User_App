package beresident.prototype.beresidentuserapp.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import beresident.prototype.beresidentuserapp.core.model.Authentication
import beresident.prototype.beresidentuserapp.screens.shared.showSnackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authentication: Authentication) : ViewModel(){
    val error = MutableLiveData<Any>()

    fun onCreate(email: String, password: String){
        viewModelScope.launch {
            val result: Any = authentication.invoke(email, password)
            error.postValue(handler(result as Int))

        }
    }

    private fun handler(statusCode: Int): String{
        var message = ""

        when (statusCode) {
            401, 422, 403 -> message = "El usuario no existe"
        }
        return message
    }
}