package beresident.prototype.beresidentuserapp.screens.shared

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import beresident.prototype.beresidentuserapp.ui.theme.snackError

@Composable
fun SnackbarHost(snackbarHostState: SnackbarHostState){
    SnackbarHost( hostState = snackbarHostState){
        Snackbar (
            backgroundColor = snackError,
            action = {
                IconButton(onClick = {snackbarHostState.currentSnackbarData?.dismiss()}) {
                    Icon(Icons.Filled.Close, contentDescription = "Dismiss", tint = Color.White)
                }
            }
        ){
            Text(
                text = snackbarHostState.currentSnackbarData?.message?:"",
                color = Color.White,
                fontSize = 10.sp
            )
        }
    }
}