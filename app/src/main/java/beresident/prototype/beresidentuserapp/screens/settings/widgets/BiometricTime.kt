package beresident.prototype.beresidentuserapp.screens.settings.widgets

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
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BiometricTime(scope: CoroutineScope){
    val biometricStore = BiometricAuthentication(LocalContext.current)
    val getLockTime = biometricStore.getLockTime.collectAsState(initial = 0)

    val radioTime = listOf(0, 15, 30)
    val current =
        when (getLockTime.value) {
            15 -> 15
            30 -> 30
            else -> 0
        }
    val (selectedOpt, onOptSelected) = remember { mutableStateOf(radioTime[current])}

    Column() {
        Text(
            "Tiempo de solicitud biométrica",
            color = Grey,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        radioTime.forEach{ text ->
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text.toString(),
                    color = Grey,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                RadioButton(
                    modifier = Modifier.padding(0.dp),
                    selected = (text == selectedOpt),
                    onClick = {
                        onOptSelected(text)
                        scope.launch {
                            biometricStore.putLockTime(text, true)
                            println(getLockTime.value)
                        }
                    }
                )
            }
        }
    }
}