package beresident.prototype.beresidentuserapp.screens.login.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.core.services.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.services.StoreUserCredentials
import beresident.prototype.beresidentuserapp.core.services.BiometricService
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import kotlinx.coroutines.CoroutineScope

@Composable
fun BiometricAuth(
    userCredentials: StoreUserCredentials,
    biometricService: BiometricService,
    biometricStore: BiometricAuthentication,
    scope: CoroutineScope,
    navController: NavController,
    onBiometricSuccess: () -> Unit,
    login: () -> Unit,
    attempts: Int,
    lockTime: Int
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        IconButton(onClick = {
            biometricService.requestBiometricAuthentication(
                biometricStore,
                userCredentials,
                scope,
                navController,
                succeedAction = {
                    onBiometricSuccess()
                    login()},
                attempts = attempts,
                lockTime = lockTime
            )

        }) {
            Icon(
                painter = painterResource(R.drawable.ic_fingerprint),
                contentDescription = "fingerprint",
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier.height(24.dp)
            )
        }
        Text(
            text = "Autentificacion biometrica",
            color = Grey,
            fontSize = 10.sp
        )
    }
}