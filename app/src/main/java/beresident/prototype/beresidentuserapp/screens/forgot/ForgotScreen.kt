package beresident.prototype.beresidentuserapp.screens.forgot

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import beresident.prototype.beresidentuserapp.screens.shared.CustomButton
import beresident.prototype.beresidentuserapp.screens.shared.CustomTextField
import beresident.prototype.beresidentuserapp.screens.shared.CustomTopBar

class ForgotScreen (forgotViewModel: ForgotViewModel): ComponentActivity() {
    var forgot = forgotViewModel

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun Screen(navController: NavController){
        val emailState = remember { CustomTextField() }

        val scaffoldState = rememberScaffoldState()
        val snackbarHostState = SnackbarHostState()

        Scaffold (backgroundColor = MaterialTheme.colors.primaryVariant, scaffoldState = scaffoldState){
            Column (modifier = Modifier.padding(bottom = DefaultTheme.dimens.grid_1_5)){
                CustomTopBar(
                    stringResource(R.string.recover_password),
                    action = {navController.popBackStack()}
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
                ) {
                    Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_3_5))
                    CustomTextField(emailState, stringResource(R.string.email), bottomPadding = DefaultTheme.dimens.grid_1_5)
                    Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_1))
                    Text(
                        stringResource(R.string.valid_email),
                        fontSize = 12.sp,
                        color = Grey,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 24.dp)
                    )
                    CustomButton(stringResource(R.string.send_verification_link), action = {
                        forgot.onCreate(emailState.text, snackbarHostState, navController)
                    })
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
}


