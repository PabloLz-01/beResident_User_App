package beresident.prototype.beresidentuserapp.screens.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .background(MaterialTheme.colors.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(65.dp)
                .padding(
                    horizontal = DefaultTheme.dimens.grid_0_5,
                    vertical = DefaultTheme.dimens.grid_1_5
                )
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
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(
                    MaterialTheme.colors.primaryVariant,
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
        )
    }
}

