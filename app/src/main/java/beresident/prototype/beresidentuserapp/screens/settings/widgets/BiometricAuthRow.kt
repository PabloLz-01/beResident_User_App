package beresident.prototype.beresidentuserapp.screens.settings.widgets

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beresident.prototype.beresidentuserapp.core.services.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.services.StoreUserCredentials
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import kotlinx.coroutines.launch

class BiometricAuthRow{
    var value: Boolean by mutableStateOf(false)
}

@Composable
fun BiometricAuthRow(
    text: String,
    action: () -> Unit,
    context: Context,
    switch: BiometricAuthRow = remember{ BiometricAuthRow()},
){
    val userCredentials = StoreUserCredentials(context)
    val userEmail = userCredentials.getEmail.collectAsState(initial = "")
    val userPassword = userCredentials.getPassword.collectAsState(initial = "")
    val scope = rememberCoroutineScope()

    val biometricStore = BiometricAuthentication(LocalContext.current)
    val aux = biometricStore.getBiometricAuthentication.collectAsState(initial = false)
    switch.value = aux.value!!

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text,
            color = Grey,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = switch.value,
            onCheckedChange = {switch.value = it
                scope.launch {
                    if (switch.value){
                        if (userEmail.value != "" || userPassword.value != "") {
                            action()
                        }
                        biometricStore.putBiometricAuthentication(true)
                    } else {
                        biometricStore.putBiometricAuthentication(false)
                    }
                }},
            enabled = true,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.secondary,
                checkedTrackColor = MaterialTheme.colors.secondary
            )
        )
    }
}