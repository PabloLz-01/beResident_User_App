package beresident.prototype.beresidentuserapp.core.usecases

import androidx.compose.runtime.MutableState
import beresident.prototype.beresidentuserapp.core.model.*
import beresident.prototype.beresidentuserapp.core.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthUsecases(
) {
    private val DEV_BASE_URL = "http://192.168.11.117:2400"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(DEV_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)
    var result: Any = 404

    fun login(email: String, password: String){
        val user = UserModel(email, password)
        val call: Call<LoginModel> = apiService.login(user)
        call.enqueue(object: Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if (response.code() == 200) {
                    val model: LoginModel? = response.body()
                    var resp = "Response Code : " + response.code() + "\n" + "Token : " + model!!.token
                    result = model.token
                } else {
                    result = response.code()
                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                result = t.message!!
            }
        })
    }

     fun register(
        result: MutableState<String>,
        name: String,
        lastName: String,
        phone: String,
        email: String,
        password: String
    ) {
        val user = RegisterModel(name, lastName, phone, email, password)
        val call: Call<RegisterModel> = apiService.register(user)

        call.enqueue(object: Callback<RegisterModel> {
            override fun onResponse(call: Call<RegisterModel>, response: Response<RegisterModel>) {
                val model: RegisterModel? = response.body()
                val resp = "Response Code : " + response.code() + "\n" +
                        "Name : " + model!!.name + "\n" +
                        "Lastname : " + model.lastName + "\n" +
                        "Phone : " + model.phone + "\n" +
                        "Email : " + model.email + "\n" +
                        "Password : " + model.password + "\n"
                result.value = resp
                println(resp)
            }

            override fun onFailure(call: Call<RegisterModel>, t: Throwable) {
                result.value = "Error found in: " + t.message
            }
        })
    }

    fun forgot(email: String, ) {
        val email = ForgotModel(email)
        val call: Call<URLModel> = apiService.forgot(email)

        call.enqueue(object: Callback<URLModel> {
            override fun onResponse(call: Call<URLModel>, response: Response<URLModel>) {
                if (response.code() == 200) {
                    result = response.body()!!.url
                    println(result)
                } else {
                    result = response.code()
                }

            }

            override fun onFailure(call: Call<URLModel>, t: Throwable) {
                result = "Error found in: " + t.message
            }
        })
    }
}





