package beresident.prototype.beresidentuserapp.screens.settings.widgets

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.core.misc.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.services.BiometricService
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import kotlinx.coroutines.launch

class BiometricAuthRow{
    var value: Boolean by mutableStateOf(false)
}

@Composable
fun BiometricAuthRow(
    text: String,
    context: Context,
    activity: AppCompatActivity,
    switch: BiometricAuthRow = remember{ BiometricAuthRow()}
){

    val biometricService = BiometricService(context, activity)
    val scope = rememberCoroutineScope()

    val biometricStore = BiometricAuthentication(LocalContext.current)
    val aux = biometricStore.getBiometricAuthentication.collectAsState(initial = false)
    switch.value = aux.value!!

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(verticalAlignment = Alignment.CenterVertically){
            Icon(
                painter = painterResource(R.drawable.ic_fingerprint),
                contentDescription = "fingerprint",
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier.height(24.dp)
            )
            Text(
                text,
                color = Grey,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Switch(
            checked = switch.value,
            onCheckedChange = {switch.value = it
                scope.launch {
                    if (switch.value){
                        biometricStore.putBiometricAuthentication(true)
                        biometricService.setupAuth()
                        biometricService.authenticate({},{},{println("erroooor")}, scope)

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