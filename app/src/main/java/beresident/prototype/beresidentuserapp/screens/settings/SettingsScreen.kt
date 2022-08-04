package beresident.prototype.beresidentuserapp.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import beresident.prototype.beresidentuserapp.screens.settings.widgets.SettingsRow
import beresident.prototype.beresidentuserapp.shared.CustomTextField
import beresident.prototype.beresidentuserapp.shared.CustomTopBar
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import beresident.prototype.beresidentuserapp.ui.theme.Grey2
import beresident.prototype.beresidentuserapp.ui.theme.LightGrey

@Composable
fun SettingsScreen(navController: NavController){
    val switch = remember { SettingsRow() }

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
                SettingsRow(text = "Notifications", hasOptions = false)
                SettingsRow(text = "Fraccionamientos")
            }
        }
    )
}
