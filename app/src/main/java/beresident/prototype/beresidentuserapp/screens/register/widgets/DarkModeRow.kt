package beresident.prototype.beresidentuserapp.screens.register.widgets

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beresident.prototype.beresidentuserapp.core.misc.StoreTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import kotlinx.coroutines.launch



class DarkModeRow{
    var auto: Boolean by mutableStateOf(false)
    var darkMode: Boolean by mutableStateOf(false)
}

@Composable
fun DarkModeRow( // (1)
    textOne: String,
    textTwo: String,
    switch: DarkModeRow = remember{ DarkModeRow()},
    context: Context = LocalContext.current,
) {
    val scope = rememberCoroutineScope()
    val dataStore = StoreTheme(context)
    var theme = dataStore.getTheme.collectAsState(initial = 0)

    switch.auto = if (theme.value == 0) true else false
    switch.darkMode = if (theme.value == 2) true else if (theme.value == 1) false else switch.darkMode

    Column(modifier = Modifier
        .fillMaxSize()){
        Box( // (2)
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .toggleable(
                    value = switch.auto,
                    onValueChange = {
                        switch.auto = it
                        scope.launch {
                            if (switch.auto) dataStore.saveTheme(0)
                            else if (switch.darkMode) dataStore.saveTheme(2)
                            else if (!switch.darkMode) dataStore.saveTheme(1)
                            println(theme.value)
                        }
                    },
                    role = Role.Switch,
                    enabled = true,
                )
        ) {
            Text( // (3)
                text = textOne,
                color = Grey,
                fontSize = 12.sp,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )

            Switch( // (3)
                checked = switch.auto,
                modifier = Modifier.align(Alignment.CenterEnd),
                onCheckedChange = {
                    switch.auto = it
                    scope.launch {
                        if (switch.auto) dataStore.saveTheme(0)
                        else if (switch.darkMode) dataStore.saveTheme(2)
                        else if (!switch.darkMode) dataStore.saveTheme(1)
                        println(theme.value)
                    }
                }, // (4)
                enabled = true,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.secondary,
                    checkedTrackColor = MaterialTheme.colors.secondary
                ),
            )
        }
        //////////////////////////////////////////////////////////////////////////////////////
        Box( // (2)
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .toggleable(
                    // (4)
                    value = switch.darkMode,
                    onValueChange = {
                        switch.darkMode = it
                        scope.launch {
                            if (switch.darkMode) dataStore.saveTheme(2) else dataStore.saveTheme(1)
                            println(theme.value)
                        }
                    },
                    role = Role.Switch,
                    enabled = !switch.auto,
                )

        ) {

            Text( // (3)
                text = textTwo,
                color = Grey,
                fontSize = 12.sp,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )

            Switch( // (3)
                checked = switch.darkMode,
                modifier = Modifier.align(Alignment.CenterEnd),
                onCheckedChange = {switch.darkMode = it
                    scope.launch {
                        if (switch.darkMode) dataStore.saveTheme(2) else dataStore.saveTheme(1)
                        println(theme.value)
                    }
                },// (4)
                enabled = !switch.auto,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.secondary,
                    checkedTrackColor = MaterialTheme.colors.secondary
                ),
            )
        }
    }
}