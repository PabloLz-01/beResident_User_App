package beresident.prototype.beresidentuserapp.screens.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
class CustomRadioButton {
    var selected: Boolean by mutableStateOf(false)
}

@Composable
fun CustomRadioButton(
    values: CustomRadioButton = remember { CustomRadioButton() },
    action: () -> Unit
) {
    Card(
        modifier = Modifier.background(Color.Transparent)
            .clip(RoundedCornerShape(16.dp)),
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, color = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(if (values.selected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface)
                .clickable(onClick = action)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(13.dp)
                    .background(MaterialTheme.colors.primaryVariant)
                    .border(2.dp, color = MaterialTheme.colors.primaryVariant),
                contentAlignment = Alignment.Center

            ){
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(8.dp)
                        .background(if (values.selected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface)

                )
            }
        }
    }
}