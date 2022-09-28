package beresident.prototype.beresidentuserapp.screens.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class CircularProgress

@Composable
fun CircularIndicator(
    isDisplayed: Boolean
){
    if (isDisplayed){
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
        ){
            CircularProgressIndicator(
                color = MaterialTheme.colors.secondary
            )
        }
    }

}