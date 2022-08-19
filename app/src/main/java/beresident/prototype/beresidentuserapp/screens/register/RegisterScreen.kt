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
import beresident.prototype.beresidentuserapp.core.usecases.AuthUsecases
import beresident.prototype.beresidentuserapp.screens.shared.*
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

    var snackbarText: String = ""
    var snackbarColor: Color = snackbarError
    var showSnackbar = true

    val terminos = remember { mutableStateOf(false) }
    val aviso = remember { mutableStateOf(false) }
    val cargos = remember { mutableStateOf(false) }

    val response = remember { mutableStateOf("") }


    Scaffold (backgroundColor = MaterialTheme.colors.primaryVariant, scaffoldState = scaffoldState){

        Column {
            CustomTopBar(stringResource(R.string.register), action = {
                navController.popBackStack()
            })
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_2))
                CustomTextField(name, "Nombre(s)",  bottomPadding = DefaultTheme.dimens.grid_2)
                CustomTextField(lastName, "Apellido(s)", bottomPadding = DefaultTheme.dimens.grid_2)
                CustomPhoneField(phone, stringResource(R.string.phone_number), bottomPadding = DefaultTheme.dimens.grid_2)
                CustomTextField(email, stringResource(R.string.email), bottomPadding = DefaultTheme.dimens.grid_2)
                CustomTextField(confirmEmail, stringResource(R.string.confirm_email), bottomPadding = DefaultTheme.dimens.grid_2)
                CustomTextField(password, "Contraseña", bottomPadding = DefaultTheme.dimens.grid_2, password = true)
                CustomTextField(confirmPassword, "Confirme su contraseña",  password = true, bottomPadding = DefaultTheme.dimens.grid_1_5)
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
                CustomButton(stringResource(R.string.create_account), action = {
                    if (
                        name.text == "" ||
                        lastName.text == "" ||
                        phone.text == "" ||
                        email.text == "" ||
                        confirmEmail.text == "" ||
                        password.text == "" ||
                        confirmPassword.text == ""
                    ) {
                        snackbarText = "Por favor rellene todos los campos"
                    } else if (password.text.length <= 5 ) {
                        snackbarText = "La contraseña no es valida"
                    } else if (password.text != confirmPassword.text){
                        snackbarText = "Las contraseñas no son iguales"
                    } else if (email.text != confirmEmail.text) {
                        snackbarText = "Los correos son diferentes"
                    } else {
                        AuthUsecases().register(response, name.text, lastName.text, phone.text, email.text, password.text)
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
