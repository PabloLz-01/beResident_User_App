package beresident.prototype.beresidentuserapp.core.services

import androidx.compose.runtime.MutableState
import beresident.prototype.beresidentuserapp.core.model.LoginModel
import beresident.prototype.beresidentuserapp.core.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AuthenticationService @Inject constructor(private val apiService: ApiService) {
    var result: Any = 404

    suspend fun authenticate(email: String, password: String): Any {
        return withContext(Dispatchers.IO){
            val call: Call<LoginModel> = apiService.login(UserModel(email, password))

            var response = call.execute()
            if (response.code() == 200) result = response.code()
            else result = response.code()

        }
    }
}