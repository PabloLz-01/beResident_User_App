package beresident.prototype.beresidentuserapp.usecases

import beresident.prototype.beresidentuserapp.core.model.UserModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/signup")
    fun register(@Body userModel: UserModel): Call<UserModel>

    @POST("/api/auth/login")
    fun login(@Body userModel: UserModel): Call<UserModel>
}