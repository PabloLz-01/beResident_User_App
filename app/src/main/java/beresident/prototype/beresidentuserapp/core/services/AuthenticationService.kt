package beresident.prototype.beresidentuserapp.core.services

import beresident.prototype.beresidentuserapp.core.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.lang.Exception
import javax.inject.Inject

//Makes the call to our api, sending our data and expecting a result based in a response code
class AuthenticationService @Inject constructor(private val apiService: ApiService) {
    var result: Any = 404 //Default error code

    //Login api call
    suspend fun authenticate(email: String, password: String): Any {
        return withContext(Dispatchers.IO){
            val call: Call<LoginModel> = apiService.login(UserModel(email, password))
            try {
                val  response = call.execute()
                result = response.code()

                result
            } catch (e: Exception){
                result = 500
            }
        }
    }

    //Register api call
    suspend fun register(
        name: String,
        lastName: String,
        phone: String,
        email: String,
        password: String
    ): Any {
        return withContext(Dispatchers.IO){
            val call: Call<RegisterModel> = apiService.register(RegisterModel(name, lastName, phone, email, password))
            try {
                val  response = call.execute()
                result = response.code()

                result
            } catch (e: Exception){
                result = 500
            }
        }
    }

    //Forgot password api call
    suspend fun forgot(email: String): Any {
        return withContext(Dispatchers.IO){
            val call: Call<URLModel> = apiService.forgot(ForgotModel(email))
            try {
                val  response = call.execute()
                result = response.code()

                result
            } catch (e: Exception){
                result = 500
            }
        }
    }
}