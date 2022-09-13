package beresident.prototype.beresidentuserapp.screens.settings

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.core.services.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.services.StoreUserCredentials
import beresident.prototype.beresidentuserapp.core.services.BiometricService
import beresident.prototype.beresidentuserapp.screens.settings.widgets.BiometricAuthRow
import beresident.prototype.beresidentuserapp.screens.settings.widgets.BiometricTime
import beresident.prototype.beresidentuserapp.screens.settings.widgets.ThemeDialog
import beresident.prototype.beresidentuserapp.screens.shared.CustomTopBar
import beresident.prototype.beresidentuserapp.ui.theme.Grey

class SettingsScreen(var activity: AppCompatActivity){

    @Composable
    fun Screen(navController: NavController){
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val showDialogTheme = remember { mutableStateOf(false)}

        val userCredentials = StoreUserCredentials(context)
        val biometricStore = BiometricAuthentication(context)
        val attempts = biometricStore.getAttemps.collectAsState(initial = 0)
        val lockTime = biometricStore.getLockTime.collectAsState(initial = 0)
        val isBiometricAuth = biometricStore.getBiometricAuthentication.collectAsState(initial = false)
        val biometricService = BiometricService(context, activity)
        Scaffold (
            backgroundColor = MaterialTheme.colors.primaryVariant,
            topBar = { CustomTopBar(text = "Ajustes", action = {navController.popBackStack()}) },
            bottomBar = {},
            content = {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 16.dp)
                        .fillMaxSize()
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 16.dp)
                    ){
                        Icon(
                            painter = painterResource(R.drawable.ic_phone_iphone_fill0_wght400_grad0_opsz48),
                            contentDescription = "phone",
                            tint = MaterialTheme.colors.secondary,
                            modifier = Modifier.height(24.dp)
                        )
                        Text(
                            text = "Sistema",
                            color = Grey,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .height(48.dp)
                            .fillMaxWidth()
                            .clickable { showDialogTheme.value = true },
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            "Tema",
                            color = Grey,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (biometricService.canUseBiometric()){
                        BiometricAuthRow("Habilitar autentificacion biometrica",
                            context = context,
                            action = { biometricService.requestBiometricAuthentication(biometricStore, userCredentials,scope, navController, attempts.value!!, lockTime.value) }
                        )
                        Divider(modifier = Modifier.padding(bottom = 8.dp))
                        if (isBiometricAuth.value!!) {
                            BiometricTime(scope)
                        }
                    }
                }
                ThemeDialog(showDialog = showDialogTheme.value, context = context) {
                    showDialogTheme.value = false
                }
            }
        )
    }
}


