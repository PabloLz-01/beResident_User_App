package beresident.prototype.beresidentuserapp.screens.register

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
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
import beresident.prototype.beresidentuserapp.screens.register.widgets.*
import beresident.prototype.beresidentuserapp.screens.shared.*
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RegisterScreen(registerViewModel: RegisterViewModel): ComponentActivity() {
    var register = registerViewModel

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun Screen(navController: NavController){
        val name = remember { CustomTxtField() }
        val lastName = remember { CustomTxtField() }
        val phone = remember { CustomPhoneField() }
        val email = remember { CustomTxtField() }
        val confirmEmail = remember { CustomTxtField() }
        val password = remember { CustomTxtField() }
        val confirmPassword = remember { CustomTxtField() }
        val checkbox = remember { CustomCheckbox() }
        var progress = register.progress.value

        val snackHostState = SnackbarHostState()
        val snackTerms =  stringResource(R.string.accept_terms_advices_charges)
        var snackStatus = register.snackStatus
        val snackMessage = register.snackMessage.value
        val coroutineScope = rememberCoroutineScope()

        val terms = remember { mutableStateOf(false) }
        val advices = remember { mutableStateOf(false) }
        val charges = remember { mutableStateOf(false) }

        if (snackStatus.value) {
            MainScope().launch {
                snackHostState.currentSnackbarData?.dismiss()
                snackHostState.showSnackbar(snackMessage)
                snackStatus.value = false

            }
        }


        Scaffold (
            backgroundColor = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.padding(0.dp)
        ){ padding ->
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
                    CustomTxtField(name, stringResource(R.string.name),  btmPadding = DefaultTheme.dimens.grid_2)
                    CustomTxtField(lastName, stringResource(R.string.last_name), btmPadding = DefaultTheme.dimens.grid_2)
                    CustomPhoneField(phone, stringResource(R.string.phone_number), bottomPadding = DefaultTheme.dimens.grid_2)
                    CustomTxtField(email, stringResource(R.string.email), btmPadding = DefaultTheme.dimens.grid_2)
                    CustomTxtField(confirmEmail, stringResource(R.string.confirm_email), btmPadding = DefaultTheme.dimens.grid_2)
                    CustomTxtField(password, stringResource(R.string.password), btmPadding = DefaultTheme.dimens.grid_2, userPassword = true)
                    CustomTxtField(confirmPassword, stringResource(R.string.confirm_password),  userPassword = true, btmPadding = DefaultTheme.dimens.grid_1_5)
                    CustomCheckbox(
                        checkbox,
                        stringResource(R.string.checkbox_terms),
                        action = {
                            checkbox.isCheck = !checkbox.isCheck
                            if (checkbox.isCheck) {
                                terms.value = true
                                advices.value =  true
                                charges.value =  true
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_2))
                    CustomOutlineBtn(
                        text = stringResource(R.string.create_account),
                        textColor = Color.White,
                        background = MaterialTheme.colors.secondary,
                        action = {
                            if (checkbox.isCheck){
                                register.onCreate(
                                    name.text,
                                    lastName.text,
                                    phone.number, email.text,
                                    password.text,
                                    snackHostState,
                                    navController
                                )
                            }else {
                                coroutineScope.launch {
                                    snackHostState.currentSnackbarData?.dismiss()
                                    snackHostState.showSnackbar(snackTerms)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(bottom = DefaultTheme.dimens.grid_2))
                }
            }
            Terms(terms.value){ terms.value = false }
            Advice(advices.value) { advices.value = false }
            Charge(charges.value) { charges.value = false }
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
}

