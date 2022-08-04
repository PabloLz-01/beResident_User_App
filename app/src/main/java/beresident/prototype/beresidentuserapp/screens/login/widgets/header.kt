package beresident.prototype.beresidentuserapp.screens.login.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.R

@Composable
fun AppHeader(
    action: () -> Unit
) {
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
            .height(DefaultTheme.dimens.grid_4_5),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

        ) {
        Box(
            modifier = Modifier
                .width(180.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_beresident),
                contentDescription = "App logo",
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
                Icons.Filled.List,
                contentDescription = "Menu",
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

