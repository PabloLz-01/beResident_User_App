package beresident.prototype.beresidentuserapp.screens.settings.widgets

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import beresident.prototype.beresidentuserapp.core.misc.StoreTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import kotlinx.coroutines.launch

class SettingsRow: ComponentActivity() {
    var value: Boolean by mutableStateOf(false)
}

@Composable
fun SettingsRow(
    switch: SettingsRow = remember{SettingsRow()},
    text: String,
    hasOptions: Boolean = true,
    isButton: Boolean = false,
    isDropdown: Boolean = false,
    context: Context = LocalContext.current,
    action: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val dataStore = StoreTheme(context)
    var themeValue = dataStore.getTheme.collectAsState(initial = 0)
    switch.value = when (themeValue.value) {
        2 -> true
        else -> false
    }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        Text(
            text = text,
            color = Grey,
            fontSize = 12.sp,
            modifier = Modifier.clickable(onClick = action)
        )
        if (!isButton || isDropdown) {
            if (hasOptions) {
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .width(600.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        Icons.Outlined.ArrowForward,
                        contentDescription = "next",
                        tint = Grey
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .width(600.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Switch(
                        checked = switch.value,
                        onCheckedChange = {
                            switch.value = it
                            scope.launch {
                                if (switch.value) dataStore.saveTheme(2) else dataStore.saveTheme(1)
                            }},
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.secondary,
                            checkedTrackColor = MaterialTheme.colors.secondary
                        )
                    )
                }
            }
        }
    }
}