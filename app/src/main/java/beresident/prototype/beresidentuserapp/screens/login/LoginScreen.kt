package beresident.prototype.beresidentuserapp.screens.login

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.core.misc.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import beresident.prototype.beresidentuserapp.screens.login.widgets.AppHeader
import beresident.prototype.beresidentuserapp.screens.login.widgets.Division
import beresident.prototype.beresidentuserapp.screens.login.widgets.LoginHeader
import beresident.prototype.beresidentuserapp.screens.shared.*
import kotlinx.coroutines.*

class LoginScreen(loginViewModel: LoginViewModel) : AppCompatActivity() {
    var login = loginViewModel


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun Screen(navController: NavController, authentication: () -> Unit) {
        val emailState = remember { CustomTextField() }
        val passwordState = remember { CustomTextField() }
        val checkbox =  remember { CustomCheckbox() }

        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState = SnackbarHostState()
        val context = LocalContext.current

        val biometricStore = BiometricAuthentication(LocalContext.current)
        val biometricAuthentication = biometricStore.getBiometricAuthentication
            .collectAsState(initial = false)

        Scaffold (backgroundColor = MaterialTheme.colors.primaryVariant,){
            Column (modifier = Modifier.padding(bottom = DefaultTheme.dimens.grid_1_5)){
                AppHeader(action = { navController.navigate(Screen.SettingsScreen.route) })
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
                        coroutineScope.launch {
                            login.onCreate(
                                emailState.text,
                                passwordState.text,
                                checkbox.isCheck,
                                context,
                                snackbarHostState,
                                navController,
                            )
                            if (biometricAuthentication.value!!){
                                authentication()
                            }
                        }
                    })
                    Division()
                    OutlinedButton(modifier = Modifier
                        .fillMaxWidth()
                        .height(DefaultTheme.dimens.grid_7),
                        onClick = {navController.navigate(Screen.RegisterScreen.route)},
                    ) {
                        Text(stringResource(R.string.create_an_account), color = Grey)
                    }
                }
            }
            BoxWithConstraints (
                Modifier
                    .fillMaxSize()
                    .padding(DefaultTheme.dimens.grid_2)){
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    SnackbarHost(snackbarHostState)
                }
            }
        }
    }

}



