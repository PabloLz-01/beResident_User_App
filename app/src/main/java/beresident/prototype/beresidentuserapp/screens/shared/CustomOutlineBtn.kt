package beresident.prototype.beresidentuserapp.screens.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey

@Composable
fun CustomOutlineBtn(
    text: String,
    action: () -> Unit,
    textColor: Color? = null,
    background: Color? = null,
    foreground: Color? = null
) {
    val txtColor = textColor ?: Grey
    val bg = background ?: MaterialTheme.colors.primaryVariant
    val fg = foreground ?: MaterialTheme.colors.secondary

    OutlinedButton(
        modifier = Modifier.height(DefaultTheme.dimens.grid_6).fillMaxWidth(),
        onClick = action,
        shape = CircleShape,
        elevation = elevation(
            pressedElevation = 0.dp
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = bg,
        ),
        border = BorderStroke(1.dp, fg)
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = txtColor
        )
    }
}