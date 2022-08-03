package beresident.prototype.beresidentuserapp.screens.register.widgets

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

const val textoMuerto = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla suscipit neque a augue accumsan laoreet. Aliquam quis ligula ac leo euismod mattis. Praesent eget turpis lorem. Ut fringilla scelerisque erat. Nulla id est eget risus fermentum ultricies vitae quis dolor. Duis et odio nec erat consectetur sollicitudin. Nullam bibendum sapien non nunc lobortis auctor. Suspendisse nec eleifend lacus, aliquet accumsan metus. Proin bibendum quam vitae augue tempor porta. Fusce mattis sodales rhoncus. Nulla elementum viverra egestas.\n"
@Composable
fun TerminosCondiciones(showDialog : Boolean, dismissDialog : () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { dismissDialog() },
            title = { Text(text = "Cargos Periodicos", color = Color.Black) },
            text = { Text(textoMuerto, color = Color.Black) },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissDialog()
                    }
                ) {
                    Text("Aceptar", color = Color.Black)
                }
            },
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    }
}
@Composable
fun AvisoPrivacidad(showDialog : Boolean, dismissDialog : () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { dismissDialog() },
            title = { Text(text = "Aviso de Privacidad", color = Color.Black) },
            text = { Text( textoMuerto, color = Color.Black) },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissDialog()
                    }
                ) {
                    Text("Aceptar", color = Color.Black)
                }
            },
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    }
}
@Composable
fun CargosPeriodicos(showDialog : Boolean, dismissDialog : () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { dismissDialog() },
            title = { Text(text = "Terminos y Condiciones", color = Color.Black) },
            text = { Text(textoMuerto, color = Color.Black) },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissDialog()
                    }
                ) {
                    Text("Aceptar", color = Color.Black)
                }
            },
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    }
}