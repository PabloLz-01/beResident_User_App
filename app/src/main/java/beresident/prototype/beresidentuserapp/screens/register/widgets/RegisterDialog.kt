package beresident.prototype.beresidentuserapp.screens.register.widgets

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import beresident.prototype.beresidentuserapp.R

@Composable
fun Terms(showDialog : Boolean, dismissDialog : () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { dismissDialog() },
            title = { Text(stringResource(R.string.terms), color = Color.Black) },
            text = { Text(stringResource(R.string.dead_text), color = Color.Black) },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissDialog()
                    }
                ) {
                    Text(stringResource(R.string.accept), color = Color.Black)
                }
            },
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    }
}
@Composable
fun Advice(showDialog : Boolean, dismissDialog : () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { dismissDialog() },
            title = { Text(stringResource(R.string.privacy_advice), color = Color.Black) },
            text = { Text(stringResource(R.string.dead_text), color = Color.Black) },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissDialog()
                    }
                ) {
                    Text(stringResource(R.string.accept), color = Color.Black)
                }
            },
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    }
}
@Composable
fun Charge(showDialog : Boolean, dismissDialog : () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { dismissDialog() },
            title = { Text(stringResource(R.string.periodic_charges), color = Color.Black) },
            text = { Text(stringResource(R.string.dead_text), color = Color.Black) },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissDialog()
                    }
                ) {
                    Text(stringResource(R.string.accept), color = Color.Black)
                }
            },
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    }
}