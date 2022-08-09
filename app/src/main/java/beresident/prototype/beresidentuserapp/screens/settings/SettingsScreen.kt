package beresident.prototype.beresidentuserapp.screens.settings

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.BaseApplication
import beresident.prototype.beresidentuserapp.MainActivity
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.core.misc.StoreTheme
import beresident.prototype.beresidentuserapp.screens.register.widgets.AvisoPrivacidad
import beresident.prototype.beresidentuserapp.screens.register.widgets.CargosPeriodicos
import beresident.prototype.beresidentuserapp.screens.register.widgets.TerminosCondiciones
import beresident.prototype.beresidentuserapp.screens.settings.widgets.SettingsRow
import beresident.prototype.beresidentuserapp.shared.CustomTopBar
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import beresident.prototype.beresidentuserapp.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

var prueba = false

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(navController: NavController){
    val context = LocalContext.current

    val switch = remember { SettingsRow()}

    val terminos = remember { mutableStateOf(false) }
    val aviso = remember { mutableStateOf(false) }
    val cargos = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val dataStore = StoreTheme(context)
    var themeValue = dataStore.getTheme.collectAsState(initial = false)
    switch.value = themeValue.value!!

    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Scaffold (

        backgroundColor = MaterialTheme.colors.primaryVariant,
        topBar = { CustomTopBar(text = "Settings", action = {navController.popBackStack()}) },
        bottomBar = {},
        content = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .fillMaxSize()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 16.dp)
                ){
                    Icon(
                        Icons.Outlined.AccountCircle,
                        contentDescription = "account",
                        tint = MaterialTheme.colors.secondary
                    )
                    Text(
                        text = "Cuenta",
                        color = Grey,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                SettingsRow(text = "Editar perfil")
                SettingsRow(text = "Cambiar contraseña")
                SettingsRow(text = "Fraccionamientos")
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedOptionText,
                        onValueChange = { },
                        label = { Text("Label") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOptionText = selectionOption
                                    expanded = false
                                }
                            ) {
                                Text(text = selectionOption)
                            }
                        }
                    }
                }
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
                        text = "System",
                        color = Grey,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                SettingsRow(text = "Modo Oscuro", hasOptions = false, context = context) {
                    switch.value = !switch.value
                    scope.launch {
                        dataStore.saveTheme(switch.value)
                    }
                }
                SettingsRow(text = "Idioma")
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 16.dp)
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_gavel_fill0_wght400_grad0_opsz48),
                        contentDescription = "Terminos y Soporte",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.height(24.dp)
                    )
                    Text(
                        text = "System",
                        color = Grey,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                SettingsRow(text = "Terminos y Condiciones", isButton = true){terminos.value = true }
                SettingsRow(text = "Aviso de Privacidad", isButton = true){aviso.value = true}
                SettingsRow(text = "Aviso de Cargos Periodicos", isButton = true){cargos.value = true}
                SettingsRow(text = "Soporte", isButton = true)
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
