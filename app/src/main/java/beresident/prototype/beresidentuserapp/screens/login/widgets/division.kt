package beresident.prototype.beresidentuserapp.screens.login.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey2

@Composable
fun Division() {
    Row (
        modifier = Modifier.padding(all = DefaultTheme.dimens.grid_1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(130.dp)
                .height(1.dp)
                .background(Grey2)
        )
        Box(
            modifier = Modifier
                .height(20.dp)
                .width(30.dp)
                .background(MaterialTheme.colors.primaryVariant)
        ){
            Text(
                "o",
                modifier = Modifier
                    .align(Alignment.Center),
                color = Grey2
            )
        }
        Box(
            modifier = Modifier
                .width(130.dp)
                .height(1.dp)
                .background(Grey2)
        )

    }
}