package beresident.prototype.beresidentuserapp.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
                CustomTextField(emailState, "Correo", bottomPadding = DefaultTheme.dimens.grid_2)
                CustomTextField(passwordState, "Contraseña", password = true, bottomPadding = DefaultTheme.dimens.grid_1_5)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = DefaultTheme.dimens.grid_1_5),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CheckBox(checkbox, text = "Recordar mi cuenta", action = {})
                    ClickableText(
                        text = AnnotatedString("Olvide mi contraseña"),
                        onClick = {navController.navigate(Screen.ForgotScreen.route)},
                        style = TextStyle(
                            color = MaterialTheme.colors.secondary,
                            fontSize = 10.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_3))
                CustomButton(text = "Iniciar sesion", action = {
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
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message = snackbarText, duration= SnackbarDuration.Short)
                    }
                })
                Division()
                OutlinedButton(modifier = Modifier
                    .fillMaxWidth()
                    .height(DefaultTheme.dimens.grid_7),
                    onClick = {navController.navigate(Screen.RegisterScreen.route)},
                ) {
                    Text(
                        "Crear cuenta",
                        color = Grey,
                    )
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