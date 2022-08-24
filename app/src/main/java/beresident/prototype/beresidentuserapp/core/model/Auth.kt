package beresident.prototype.beresidentuserapp.core.model

import beresident.prototype.beresidentuserapp.core.services.AuthenticationService
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class Authentication @Inject constructor(private val authenticationService: AuthenticationService) {
    suspend fun invoke(email: String, password: String): Any {
        authenticationService.authenticate(email, password)
        return authenticationService.result
    }

}

data class UserModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class LoginModel(
    @SerializedName("token") val token: String
)

data class RegisterModel(
    @SerializedName("name") val name: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class ForgotModel(
    @SerializedName("email") val email: String
)

data class URLModel(
    @SerializedName("url") val url: String
)
