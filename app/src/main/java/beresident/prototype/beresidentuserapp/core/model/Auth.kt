package beresident.prototype.beresidentuserapp.core.model

import com.google.gson.annotations.SerializedName

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

