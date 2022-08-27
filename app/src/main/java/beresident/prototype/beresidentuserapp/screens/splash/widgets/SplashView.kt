package beresident.prototype.beresidentuserapp.screens.splash.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SplashView() {
    Scaffold (backgroundColor = MaterialTheme.colors.primary,){
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.primary)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_beresident),
                    contentDescription = "Login beResident",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(62.dp)
                        .width(242.dp)
                )
            }
        }
    }
}