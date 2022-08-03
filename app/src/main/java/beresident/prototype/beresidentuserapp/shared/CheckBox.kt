package beresident.prototype.beresidentuserapp.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import beresident.prototype.beresidentuserapp.ui.theme.LightGrey

class CustomCheckbox {
    var isCheck: Boolean by mutableStateOf(false)
}

@Composable
fun CheckBox(
    values: CustomCheckbox = remember { CustomCheckbox() },
    text: String,
    action: () -> Unit
) {

    Row {
        Card(
            modifier = Modifier.background(LightGrey),
            elevation = 0.dp,
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.5.dp, color = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .background(if (values.isCheck) MaterialTheme.colors.secondary else LightGrey)
                    .clickable(onClick = action),
                contentAlignment = Alignment.Center
            ) {
                if(values.isCheck)
                    Icon(Icons.Default.Check, contentDescription = "", tint = Color.White)
            }
        }

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp),
            text = text,
            fontSize = 10.sp,
            color = Grey
        )
    }
}