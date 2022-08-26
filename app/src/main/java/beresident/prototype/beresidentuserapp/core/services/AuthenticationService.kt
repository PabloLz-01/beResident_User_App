package beresident.prototype.beresidentuserapp.core.services

import androidx.compose.runtime.MutableState
import beresident.prototype.beresidentuserapp.core.model.*
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

            val  response = call.execute()
            if (response.code() == 200) result = response.code()
            else result = response.code()

            result

        }
    }

    suspend fun register(
        name: String,
        lastName: String,
        phone: String,
        email: String,
        password: String
    ): Any {
        return withContext(Dispatchers.IO){
            val call: Call<RegisterModel> = apiService.register(RegisterModel(name, lastName, phone, email, password))

            val response = call.execute()
            result = response.code()

            result
        }
    }

    suspend fun forgot(email: String): Any {
        return withContext(Dispatchers.IO){
            val call: Call<URLModel> = apiService.forgot(ForgotModel(email))

            val response = call.execute()
            result = response.code()

            result
        }
    }
}