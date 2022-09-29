package beresident.prototype.beresidentuserapp.screens.login.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme

@Composable
fun LoginHeader(action: () -> Unit) {
    Column{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.primary)
                .padding(
                    top = DefaultTheme.dimens.grid_1_5,
                    bottom = DefaultTheme.dimens.grid_1_5,
                    start = 12.dp,
                    end = 12.dp
                )
                .height(DefaultTheme.dimens.grid_4),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .width(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_beresident),
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = action,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(54.dp)
                    .padding(end = DefaultTheme.dimens.grid_0_5)
            ) {
                Icon(
                    painterResource(R.drawable.ic_menu),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.width(24.dp)
                )
            }
        }
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(DefaultTheme.dimens.grid_10)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.primary)
                    .align(Alignment.TopCenter)
                    .height(DefaultTheme.dimens.grid_10)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primaryVariant,
                        RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
                    )
                    .align(Alignment.BottomCenter)
                    .height(DefaultTheme.dimens.grid_8)
            )
            Image(
                painter = painterResource(R.drawable.login),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(DefaultTheme.dimens.grid_10)
                    .width(DefaultTheme.dimens.grid_10)
            )
        }
    }
}