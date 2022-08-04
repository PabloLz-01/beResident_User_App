package beresident.prototype.beresidentuserapp.screens.splash.widgets

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import beresident.prototype.beresidentuserapp.MainActivity

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

