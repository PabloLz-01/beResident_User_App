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
import beresident.prototype.beresidentuserapp.screens.shared.CustomRadioButton
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BiometricTime(scope: CoroutineScope){
    val short = remember { CustomRadioButton() }
    val mid = remember {CustomRadioButton()}
    val long = remember {CustomRadioButton()}

    val biometricStore = BiometricAuthentication(LocalContext.current)
    val getLockTime = biometricStore.getLockTime.collectAsState(initial = 0)

    when (getLockTime.value) {
        0 -> {
            short.selected = true
            mid.selected = false
            long.selected = false
        }
        15 -> {
            short.selected = false
            mid.selected = true
            long.selected = false
        }
        30 -> {
            short.selected = false
            mid.selected = false
            long.selected = true
        }
    }

    Column {
        Text(
            "Tiempo de solicitud biométrica",
            color = Grey,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "0 min",
                color = Grey,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            CustomRadioButton(short, action = {
                scope.launch {
                    short.selected = true
                    biometricStore.putLockTime(0, true)
                    mid.selected = false
                    long.selected = false
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
                "15 min",
                color = Grey,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            CustomRadioButton(mid, action = {
                scope.launch {
                    mid.selected = true
                    biometricStore.putLockTime(15, true)
                    short.selected = false
                    long.selected = false
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
                "30 min",
                color = Grey,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            CustomRadioButton(long, action = {
                scope.launch {
                    long.selected = true
                    biometricStore.putLockTime(30, true)
                    mid.selected = false
                    short.selected = false
                }
            })
        }

    }
}