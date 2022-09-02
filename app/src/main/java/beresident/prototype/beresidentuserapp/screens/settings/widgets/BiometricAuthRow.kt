package beresident.prototype.beresidentuserapp.screens.settings.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beresident.prototype.beresidentuserapp.core.misc.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.misc.StoreUserCredentials
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import kotlinx.coroutines.launch

class BiometricAuthRow{
    var value: Boolean by mutableStateOf(false)
}

@Composable
fun BiometricAuthRow(
    text: String,
    switch: BiometricAuthRow = remember{ BiometricAuthRow()}
){
    var scope = rememberCoroutineScope()

    val biometricStore = BiometricAuthentication(LocalContext.current)
    val aux = biometricStore.getBiometricAuthentication.collectAsState(initial = false)
    switch.value = aux.value!!

    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .height(48.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text,
            color = Grey,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = switch.value,
            onCheckedChange = {switch.value = it
                              scope.launch {
                                  if (switch.value){
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