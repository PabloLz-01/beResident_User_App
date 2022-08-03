package beresident.prototype.beresidentuserapp.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.shared.CheckBox
import beresident.prototype.beresidentuserapp.shared.CustomButton
import beresident.prototype.beresidentuserapp.shared.CustomCheckbox
import beresident.prototype.beresidentuserapp.shared.CustomTextField
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import beresident.prototype.beresidentuserapp.ui.theme.snackbarError
import beresident.prototype.beresidentuserapp.screens.login.widgets.AppHeader
import beresident.prototype.beresidentuserapp.screens.login.widgets.Division
import beresident.prototype.beresidentuserapp.screens.login.widgets.LoginHeader
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController){
    val emailState = remember { CustomTextField() }
    val passwordState = remember { CustomTextField() }
    var checkbox =  remember { CustomCheckbox() }

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()
    var showSnackbar = true

    var snackbarText: String
    var snackbarColor: Color = snackbarError


    Scaffold (
        backgroundColor = Color.White,
        scaffoldState = scaffoldState,
    ){
        Column (modifier = Modifier.padding(bottom = DefaultTheme.dimens.grid_1_5)){
            AppHeader()
            LoginHeader()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                CustomTextField(emailState, stringResource(R.string.email), bottomPadding = DefaultTheme.dimens.grid_2)
                CustomTextField(passwordState, "Contraseña", password = true, bottomPadding = DefaultTheme.dimens.grid_1_5)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = DefaultTheme.dimens.grid_1_5),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CheckBox(checkbox, stringResource(R.string.remember_me), action = {})
                    ClickableText(
                        text = AnnotatedString(stringResource(R.string.forgot_password)),
                        onClick = {navController.navigate(Screen.ForgotScreen.route)},
                        style = TextStyle(
                            color = MaterialTheme.colors.secondary,
                            fontSize = 11.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_3))
                CustomButton(stringResource(R.string.login), action = {
                    if (emailState.text == "" || passwordState.text == "") {
                        snackbarText = "Por favor rellene todos los campos"
                        snackbarColor = snackbarError
                    } else if (passwordState.text.length <= 5 ) {
                        snackbarText = "La contraseña no es valida"
                        snackbarColor = snackbarError
                    } else {
                        snackbarText = "Iniciando sesion..."
                        snackbarColor = Color.Green
                        navController.navigate(Screen.MainScreen.route)
                    }
                    if (showSnackbar){
                        showSnackbar = false
                        coroutineScope.launch {
                            var snackbarResult = snackbarHostState.showSnackbar(message = snackbarText, duration= SnackbarDuration.Short)
                            when (snackbarResult){
                                SnackbarResult.Dismissed -> showSnackbar = true
                                SnackbarResult.ActionPerformed -> showSnackbar = true
                            }
                        }
                    }
                })
                Division()
                OutlinedButton(modifier = Modifier
                    .fillMaxWidth()
                    .height(DefaultTheme.dimens.grid_7),
                    onClick = {navController.navigate(Screen.RegisterScreen.route)},
                ) {
                    Text(stringResource(R.string.create_account), color = Grey,)
                }
            }
        }
        BoxWithConstraints (
            Modifier
                .fillMaxSize()
                .padding(DefaultTheme.dimens.grid_2)){
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                beresident.prototype.beresidentuserapp.shared.SnackbarHost(
                    snackbarHostState,
                    snackbarColor
                )
            }
        }
    }
}