package beresident.prototype.beresidentuserapp

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application(){
    //Should be saved in data store
    val isDark =  mutableStateOf(false)

    fun toggleLightTheme() {
        isDark.value = !isDark.value
    }
}