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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.BaseApplication
import beresident.prototype.beresidentuserapp.MainActivity
import beresident.prototype.beresidentuserapp.core.misc.StoreTheme
import beresident.prototype.beresidentuserapp.screens.settings.widgets.SettingsRow
import beresident.prototype.beresidentuserapp.shared.CustomTopBar
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import beresident.prototype.beresidentuserapp.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

var prueba = false

@Composable
fun SettingsScreen(navController: NavController){
    val context = LocalContext.current
    val switch = remember { SettingsRow() }
    val scope = rememberCoroutineScope()
    val dataStore = StoreTheme(context)
    val mCheckedState = remember{ mutableStateOf(false)}

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
                        text = "Account",
                        color = Grey,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                SettingsRow(text = "Edit profile")
                SettingsRow(text = "Notifications")
                SettingsRow(text = "Fraccionamientos")
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
                        text = "Account",
                        color = Grey,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                SettingsRow(text = "Edit profile")
                SettingsRow(text = "Notifications", hasOptions = false, context = context)
                SettingsRow(text = "Fraccionamientos")
            }
        }
    )
}
