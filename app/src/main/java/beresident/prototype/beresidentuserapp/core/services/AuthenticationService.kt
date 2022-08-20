package beresident.prototype.beresidentuserapp.core.services

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

            call.enqueue(object: Callback<LoginModel> {
                override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                    result = if (response.code() == 200) {
                        val model: LoginModel? = response.body()
                        model!!.token
                    } else response.code()
                }

                override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                    result = t.message!!
                }
            })

            result
        }
    }
}