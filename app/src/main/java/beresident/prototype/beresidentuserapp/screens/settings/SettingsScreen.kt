package beresident.prototype.beresidentuserapp.screens.settings

import android.annotation.SuppressLint
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
import beresident.prototype.beresidentuserapp.screens.register.widgets.AvisoPrivacidad
import beresident.prototype.beresidentuserapp.screens.register.widgets.CargosPeriodicos
import beresident.prototype.beresidentuserapp.screens.register.widgets.DarkModeRow
import beresident.prototype.beresidentuserapp.screens.register.widgets.TerminosCondiciones
import beresident.prototype.beresidentuserapp.screens.shared.CustomTopBar
import beresident.prototype.beresidentuserapp.ui.theme.Grey

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController){
    val context = LocalContext.current

    val terminos = remember { mutableStateOf(false) }
    val aviso = remember { mutableStateOf(false) }
    val cargos = remember { mutableStateOf(false) }

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
                DarkModeRow(textOne = "Tema de el sistema", textTwo = "Modo Oscuro", context = context)
            }
            TerminosCondiciones(terminos.value){
                terminos.value = false
            }
            AvisoPrivacidad(aviso.value) {
                aviso.value = false
            }
            CargosPeriodicos(cargos.value) {
                cargos.value = false
            }
        }
    )
}
