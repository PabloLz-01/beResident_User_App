package beresident.prototype.beresidentuserapp.screens.login

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.core.misc.StoreUserCredentials
import beresident.prototype.beresidentuserapp.screens.shared.CheckBox
import beresident.prototype.beresidentuserapp.screens.shared.CustomButton
import beresident.prototype.beresidentuserapp.screens.shared.CustomCheckbox
import beresident.prototype.beresidentuserapp.screens.shared.CustomTextField
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import beresident.prototype.beresidentuserapp.ui.theme.snackbarError
import beresident.prototype.beresidentuserapp.screens.login.widgets.AppHeader
import beresident.prototype.beresidentuserapp.screens.login.widgets.Division
import beresident.prototype.beresidentuserapp.screens.login.widgets.LoginHeader
import beresident.prototype.beresidentuserapp.core.usecases.AuthUsecases
import kotlinx.coroutines.*

class LoginScreen: ComponentActivity(){
    private val loginViewModel: LoginViewModel by viewModels()

    fun login(email: String, password: String) {
        loginViewModel.onCreate(email, password)

        loginViewModel.error.observe(this, Observer {
            val error = it.toString()
        })
    }
}


@Composable
fun LoginScreen(navController: NavController) {
    val emailState = remember { CustomTextField() }
    val passwordState = remember { CustomTextField() }
    var checkbox =  remember { CustomCheckbox() }

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()
    var showSnackbar = true

    var snackbarText: String = "La cuenta no existe"

    var context = LocalContext.current
    val response = remember { mutableStateOf("") }
    val userStore = StoreUserCredentials(context)

    val auth = AuthUsecases()

    val loginScreen: LoginScreen = remember{ LoginScreen() }

    Scaffold (
        backgroundColor = MaterialTheme.colors.primaryVariant,
        scaffoldState = scaffoldState,
    ){
        Column (modifier = Modifier.padding(bottom = DefaultTheme.dimens.grid_1_5)){
            AppHeader(action = {
                navController.navigate(Screen.SettingsScreen.route)
            })
            LoginHeader()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                CustomTextField(emailState, stringResource(R.string.email), bottomPadding = DefaultTheme.dimens.grid_2)
                CustomTextField(passwordState, "Contraseña", password = true)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = DefaultTheme.dimens.grid_1_5,
                            top = DefaultTheme.dimens.grid_1
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CheckBox(checkbox, stringResource(R.string.remember_me), action = {
                        checkbox.isCheck = !checkbox.isCheck
                    })
                    Text(
                        stringResource(R.string.forgot_password),
                        fontSize = 11.sp,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.clickable{
                            coroutineScope.launch {
                                navController.navigate(Screen.ForgotScreen.route)
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_3))
                CustomButton(stringResource(R.string.login), action = {
                    loginScreen.login("test@gmail.com", "wer123")
                    /*if (emailState.text == "" || passwordState.text == "") {
                        snackbarText = "Por favor rellene todos los campos"
                    } else if (passwordState.text.length <= 5 ) {
                        snackbarText = "La contraseña no es valida"
                    } else {
                        loginScreen.login(emailState.text, passwordState.text)
                        auth.login(response, emailState.text, passwordState.text)
                        coroutineScope.launch {
                            delay(1000)
                            when (auth.result) {
                                is String -> {
                                    showSnackbar = false
                                    if (checkbox.isCheck) {
                                        coroutineScope.launch {
                                            userStore.saveEmail(emailState.text)
                                            userStore.savePassword(passwordState.text)
                                        }
                                    }
                                    AuthUsecases().login(response, emailState.text, passwordState.text)
                                    navController.navigate(Screen.MainScreen.route)
                                }
                                else -> {
                                    snackbarText = "La cuenta no existe"
                                }
                            }
                        }
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
                    }*/
                })
                Division()
                OutlinedButton(modifier = Modifier
                    .fillMaxWidth()
                    .height(DefaultTheme.dimens.grid_7),
                    onClick = {navController.navigate(Screen.RegisterScreen.route)},
                ) {
                    Text(stringResource(R.string.create_an_account), color = Grey,)
                }
            }
        }
        BoxWithConstraints (
            Modifier
                .fillMaxSize()
                .padding(DefaultTheme.dimens.grid_2)){
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                beresident.prototype.beresidentuserapp.screens.shared.SnackbarHost(
                    snackbarHostState,
                )
            }
        }
    }
}

/*CustomButton(stringResource(R.string.login), action = {
    if (emailState.text == "" || passwordState.text == "") {
        snackbarText = "Por favor rellene todos los campos"
    } else if (passwordState.text.length <= 5) {
        snackbarText = "La contraseña no es valida"
    } else {
        login()
        auth.login(response, emailState.text, passwordState.text)
        coroutineScope.launch {
            delay(1000)
            when (auth.result) {
                is String -> {
                    showSnackbar = false
                    if (checkbox.isCheck) {
                        coroutineScope.launch {
                            userStore.saveEmail(emailState.text)
                            userStore.savePassword(passwordState.text)
                        }
                    }
                    AuthUsecases().login(response, emailState.text, passwordState.text)
                    navController.navigate(Screen.MainScreen.route)
                }
                else -> {
                    snackbarText = "La cuenta no existe"
                }
            }
        }
    }
}*/