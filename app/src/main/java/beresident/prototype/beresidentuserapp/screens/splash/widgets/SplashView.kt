package beresident.prototype.beresidentuserapp.screens.splash.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import beresident.prototype.beresidentuserapp.R

@Composable
fun SplashView() {
    Scaffold (backgroundColor = MaterialTheme.colors.primary,){
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.primary)
                    .align(Alignment.TopStart)
                    .padding(bottom = 50.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_beresident),
                    contentDescription = "Login beResident",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(70.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_splash_img),
                    contentDescription = "splash img",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}