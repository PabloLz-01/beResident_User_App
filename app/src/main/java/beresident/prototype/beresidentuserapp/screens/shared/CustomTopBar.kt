package beresident.prototype.beresidentuserapp.screens.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme

@Composable
fun CustomTopBar(text: String, action: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .padding(
                horizontal = DefaultTheme.dimens.grid_0_5,
                vertical = DefaultTheme.dimens.grid_1_5
            )
            .height(DefaultTheme.dimens.grid_4_5),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = action,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(54.dp)
            ) {
                Icon(
                    Icons.Outlined.KeyboardArrowLeft,
                    contentDescription = "Menu",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Text(
                text = text,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

