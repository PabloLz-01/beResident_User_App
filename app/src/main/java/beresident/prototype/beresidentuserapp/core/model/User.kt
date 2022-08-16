package beresident.prototype.beresidentuserapp.core.model

import com.google.gson.annotations.SerializedName
import javax.inject.Inject
import javax.inject.Singleton

data class UserModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)
