package beresident.prototype.beresidentuserapp.screens.register


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.shared.*
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.snackbarError
import beresident.prototype.beresidentuserapp.screens.register.widgets.AvisoPrivacidad
import beresident.prototype.beresidentuserapp.screens.register.widgets.CargosPeriodicos
import beresident.prototype.beresidentuserapp.screens.register.widgets.TerminosCondiciones
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController){
    val name = remember { CustomTextField() }
    val lastName = remember { CustomTextField() }
    val phone = remember { CustomTextField() }
    val email = remember { CustomTextField() }
    val confirmEmail = remember { CustomTextField() }
    val password = remember { CustomTextField() }
    val confirmPassword = remember { CustomTextField() }
    val checkbox = remember { CustomCheckbox() }

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()

    var snackbarText: String
    var snackbarColor: Color = snackbarError

    val terminos = remember { mutableStateOf(false) }
    val aviso = remember { mutableStateOf(false) }
    val cargos = remember { mutableStateOf(false) }

    Scaffold (backgroundColor = Color.White, scaffoldState = scaffoldState){

        Column {
            HeaderTitle(stringResource(R.string.register), prevRoute = Screen.LoginScreen.route, navController)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_2))
                CustomTextField(name, "Nombre(s)",  bottomPadding = DefaultTheme.dimens.grid_1_5)
                CustomTextField(lastName, "Apellido(s)", bottomPadding = DefaultTheme.dimens.grid_1_5)
                CustomTextField(phone, stringResource(R.string.phone_number), bottomPadding = DefaultTheme.dimens.grid_1_5)
                CustomTextField(email, stringResource(R.string.email), bottomPadding = DefaultTheme.dimens.grid_1_5)
                CustomTextField(confirmEmail, stringResource(R.string.confirm_email), bottomPadding = DefaultTheme.dimens.grid_1_5)
                CustomTextField(password, "Contraseña", bottomPadding = DefaultTheme.dimens.grid_1_5, password = true)
                CustomTextField(confirmPassword, "Confirme su contraseña",  password = true, bottomPadding = DefaultTheme.dimens.grid_1)
                CheckBox(checkbox, stringResource(R.string.checkbox_terms),
                    action = {
                        checkbox.isCheck = !checkbox.isCheck
                        if (checkbox.isCheck) {
                            terminos.value = true
                            aviso.value =  true
                            cargos.value =  true
                        }
                    })
                Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_2))
                CustomButton(stringResource(R.string.login), action = {
                    if (name.text == "" || lastName.text == "") {
                        snackbarText = "Por favor rellene todos los campos"
                        snackbarColor = snackbarError
                    } else if (password.text.length <= 5 ) {
                        snackbarText = "La contraseña no es valida"
                        snackbarColor = snackbarError
                    } else {
                        snackbarText = "Iniciando sesion..."
                        snackbarColor = Color.Green
                    }

                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message = snackbarText, duration= SnackbarDuration.Short)
                    }
                })
                Spacer(modifier = Modifier.padding(bottom = DefaultTheme.dimens.grid_2))
            }
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
        BoxWithConstraints (
            Modifier
                .fillMaxSize()
                .padding(DefaultTheme.dimens.grid_2)){
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                SnackbarHost(snackbarHostState, snackbarColor)
            }
        }
    }
}
