package beresident.prototype.beresidentuserapp.screens.settings.widgets

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import beresident.prototype.beresidentuserapp.core.services.StoreTheme
import beresident.prototype.beresidentuserapp.screens.shared.CustomButton
import beresident.prototype.beresidentuserapp.screens.shared.CustomRadioButton
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import kotlinx.coroutines.launch

@Composable
fun ThemeDialog(
    showDialog : Boolean, context: Context, dismissDialog : () -> Unit) {
    val systemTheme = remember { CustomRadioButton() }
    val lightTheme = remember {CustomRadioButton()}
    val darkTheme = remember {CustomRadioButton()}

    val dataStore = StoreTheme(context)
    val scope = rememberCoroutineScope()
    val theme = dataStore.getTheme.collectAsState(initial = 0)

    when (theme.value){
        0 -> {
            systemTheme.selected = true
            lightTheme.selected = false
            darkTheme.selected = false
        }
        1 -> {
            lightTheme.selected = true
            systemTheme.selected = false
            darkTheme.selected = false
        }
        2 -> {
            darkTheme.selected = true
            systemTheme.selected = false
            lightTheme.selected = false
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { dismissDialog()}
        ) {
            Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                backgroundColor = MaterialTheme.colors.primaryVariant
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Tema de la aplicacion",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Grey
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Tema del sistema",
                            color = Grey,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        CustomRadioButton(systemTheme, action = {
                            scope.launch {
                                systemTheme.selected = true
                                dataStore.saveTheme(0)
                                lightTheme.selected = false
                                darkTheme.selected = false
                            }
                        })
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Tema claro",
                            color = Grey,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        CustomRadioButton(lightTheme, action = {
                            scope.launch {
                                lightTheme.selected = true
                                dataStore.saveTheme(1)
                                systemTheme.selected = false
                                darkTheme.selected = false
                            }
                        })
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Tema oscuro",
                            color = Grey,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        CustomRadioButton(darkTheme, action = {
                            scope.launch {
                                darkTheme.selected = true
                                dataStore.saveTheme(2)
                                systemTheme.selected = false
                                lightTheme.selected = false
                            }
                        })
                    }
                    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                        CustomButton(text = "Aceptar") {
                            dismissDialog()
                        }
                    }
                }
            }    
        }
    }
}
