package beresident.prototype.beresidentuserapp.core.model

import com.google.gson.annotations.SerializedName
import javax.inject.Inject
import javax.inject.Singleton

data class UserModel(
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("dateCreated") val dateCreated: String
)

@Singleton
class UserProvider @Inject constructor() {
    var user: UserModel? = null
}
