package beresident.prototype.beresidentuserapp.screens.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.core.services.BiometricAuthentication
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.core.services.StoreUserCredentials
import beresident.prototype.beresidentuserapp.core.services.BiometricService
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import beresident.prototype.beresidentuserapp.screens.login.widgets.BiometricAuth
import beresident.prototype.beresidentuserapp.screens.login.widgets.LoginDivision
import beresident.prototype.beresidentuserapp.screens.login.widgets.LoginHeader
import beresident.prototype.beresidentuserapp.screens.shared.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class LoginScreen(loginViewModel: LoginViewModel, var activity: AppCompatActivity, private var actContext: Context) : AppCompatActivity() {
    var login = loginViewModel

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun Screen(navController: NavController) {
        val userStore = StoreUserCredentials(LocalContext.current)
        val userEmail = userStore.getEmail.collectAsState(initial = "")
        val userPassword = userStore.getPassword.collectAsState(initial = "")

        val emailState = remember { CustomTxtField() }
        val passwordState = remember { CustomTxtField() }
        val checkbox =  remember { CustomCheckbox() }
        var progress = login.progress.value

        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val snackHostState = SnackbarHostState()
        var snackStatus = login.snackStatus
        val snackMessage = login.snackMessage.value

        val biometricService = BiometricService(actContext, activity)
        val biometricStore = BiometricAuthentication(context)
        val biometricAuthentication = biometricStore.getBiometricAuthentication
            .collectAsState(initial = false)
        val attempts = biometricStore.getAttemps.collectAsState(initial = 0)
        val lockTime = biometricStore.getLockTime.collectAsState(initial = 0)

        LaunchedEffect(key1 = true){
            checkbox.isCheck = userEmail.value!! != "" && userPassword.value!! != ""
            if (checkbox.isCheck) emailState.text = userEmail.value!!
        }

         if (snackStatus.value) {
             scope.launch {
                 snackHostState.currentSnackbarData?.dismiss()
                 snackHostState.showSnackbar(snackMessage)
                 snackStatus.value = false

             }
         }

        Scaffold (backgroundColor = MaterialTheme.colors.primaryVariant){ padding ->
            Column (modifier = Modifier.padding(bottom = DefaultTheme.dimens.grid_1_5)){
                LoginHeader(action = { navController.navigate(Screen.SettingsScreen.route) })
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    CustomTxtField(emailState, stringResource(R.string.email), btmPadding = DefaultTheme.dimens.grid_2)
                    CustomTxtField(passwordState, stringResource(R.string.password), userPassword = true)
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
                        CustomCheckbox(
                            checkbox,
                            stringResource(R.string.remember_me),
                            action = {
                                checkbox.isCheck = !checkbox.isCheck
                            }
                        )
                        Text(
                            stringResource(R.string.forgot_password),
                            fontSize = 11.sp,
                            color = MaterialTheme.colors.secondary,
                            modifier = Modifier.clickable{
                                scope.launch {
                                    navController.navigate(Screen.ForgotScreen.route)
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_3))
                    CustomOutlineBtn(
                        text = stringResource(R.string.login),
                        textColor = Color.White,
                        action = {
                            login.onCreate(
                                emailState.text,
                                passwordState.text,
                                checkbox.isCheck,
                                context,
                                navController,
                            )
                        },
                        background = MaterialTheme.colors.secondary
                    )
                    LoginDivision()
                    CustomOutlineBtn(
                        text = stringResource(R.string.create_an_account),
                        action = {navController.navigate(Screen.RegisterScreen.route)},
                        foreground = Grey,
                    )
                    if (biometricAuthentication.value!! && userPassword.value!! != "" && userEmail.value!! != "" ) {
                        BiometricAuth(
                            userStore,
                            biometricService = biometricService,
                            biometricStore = biometricStore,
                            scope = scope,
                            navController = navController,
                            onBiometricSuccess = { onBiometricSuccess(emailState, passwordState, userEmail.value!!, userPassword.value!!) },
                            login = {
                                login.onCreate(
                                    emailState.text,
                                    passwordState.text,
                                    checkbox.isCheck,
                                    context,
                                    navController,
                                )
                            },
                            attempts = attempts.value!!,
                            lockTime = lockTime.value
                        )
                    }
                }
            }
            if (progress!!){
                Column(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f))
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularIndicator(isDisplayed = true)
                }
            }
            BoxWithConstraints (
                Modifier
                    .fillMaxSize()
                    .padding(DefaultTheme.dimens.grid_2)){
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    SnackbarHost(snackHostState)
                }
            }
        }
    }

    private fun onBiometricSuccess(
        emailState: CustomTxtField,
        passwordState: CustomTxtField,
        email: String,
        password: String
    ){
        emailState.text = email
        passwordState.text = password
    }
}



