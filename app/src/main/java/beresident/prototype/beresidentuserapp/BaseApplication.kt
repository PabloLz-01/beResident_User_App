package beresident.prototype.beresidentuserapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication: Application() // This class let us us Dagger & hilt dependencies