package beresident.prototype.beresidentuserapp.core.services

import beresident.prototype.beresidentuserapp.core.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {
    //AUTH ROUTES------------------------------------------------
    @POST("/api/auth/signup")
    fun register(@Body userModel: RegisterModel): Call<RegisterModel>

    @POST("/api/auth/login")
    fun login(@Body userModel: UserModel): Call<LoginModel>

    @POST("/api/auth/forgot")
    fun forgot(@Body userModel: ForgotModel): Call<URLModel>
}