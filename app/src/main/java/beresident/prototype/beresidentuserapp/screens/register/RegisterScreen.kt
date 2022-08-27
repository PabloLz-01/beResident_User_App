package beresident.prototype.beresidentuserapp.screens.register

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.screens.shared.*
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.screens.register.widgets.AvisoPrivacidad
import beresident.prototype.beresidentuserapp.screens.register.widgets.CargosPeriodicos
import beresident.prototype.beresidentuserapp.screens.register.widgets.TerminosCondiciones
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterScreen(registerViewModel: RegisterViewModel): ComponentActivity() {
    var register = registerViewModel


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun Screen(navController: NavController){
        val name = remember { CustomTextField() }
        val lastName = remember { CustomTextField() }
        val phone = remember { CustomPhoneField() }
        val email = remember { CustomTextField() }
        val confirmEmail = remember { CustomTextField() }
        val password = remember { CustomTextField() }
        val confirmPassword = remember { CustomTextField() }
        val checkbox = remember { CustomCheckbox() }

        val snackbarHostState = SnackbarHostState()
        val coroutineScope = rememberCoroutineScope()

        val terminos = remember { mutableStateOf(false) }
        val aviso = remember { mutableStateOf(false) }
        val cargos = remember { mutableStateOf(false) }

        fun requestBringIntoView(focusState: FocusState, viewItem: Int){
            if (focusState.isFocused){
                coroutineScope.launch {
                    delay(200)
                }
            }
        }

        Scaffold (
            backgroundColor = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.padding(0.dp)
        ){
            Column (
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    ){
                CustomTopBar(stringResource(R.string.register), action = {
                    navController.popBackStack()
                })
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .verticalScroll(rememberScrollState())
                        .statusBarsPadding()
                        .imePadding()
                        .navigationBarsPadding()
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
                        if (checkbox.isCheck){
                            register.onCreate(
                                name.text,
                                lastName.text,
                                phone.number, email.text,
                                password.text,
                                snackbarHostState,
                                navController
                            )
                        }else {
                            coroutineScope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar("Debe aceptar los terminos y condiciones.")
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
                    SnackbarHost(snackbarHostState)
                }
            }
        }
    }

}

