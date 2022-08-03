package beresident.prototype.beresidentuserapp.screens.login.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import beresident.prototype.beresidentuserapp.R

@Composable
fun LoginHeader() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.primary)
                .align(Alignment.TopCenter)
                .height(200.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .align(Alignment.BottomCenter)
                .height(60.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_undraw_traveling_wuyk),
            contentDescription = "Login Image",
            modifier = Modifier
                .height(150.dp)
                .align(Alignment.Center)
                .padding(start = 10.dp, end = 10.dp)
        )
    }
}