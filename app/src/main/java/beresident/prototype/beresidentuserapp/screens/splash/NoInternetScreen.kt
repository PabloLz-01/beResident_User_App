package beresident.prototype.beresidentuserapp.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.screens.shared.CustomOutlineBtn

@Composable
fun NoInternetScreen (navController: NavController){
    Scaffold (backgroundColor = MaterialTheme.colors.primary){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(125.dp)
                    .height(125.dp)
                    .background(color = MaterialTheme.colors.primary)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_wifi_off_fill0_wght400_grad0_opsz48),
                    contentDescription = "wifi connection",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                )
            }
            Text(
                stringResource(R.string.verify_your_internet_connection),
                color = Color.White,
                modifier = Modifier.padding(16.dp),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
            Box(
                Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
            ) {
                CustomOutlineBtn(
                    text = stringResource(R.string.try_again),
                    foreground = MaterialTheme.colors.secondary,
                    action = {
                        navController.navigate(Screen.SplashScreen.route)
                    }
                )
            }
        }
    }
}
