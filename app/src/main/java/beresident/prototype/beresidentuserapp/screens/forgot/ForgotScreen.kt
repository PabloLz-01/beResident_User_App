package beresident.prototype.beresidentuserapp.screens.forgot

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import beresident.prototype.beresidentuserapp.screens.shared.CustomButton
import beresident.prototype.beresidentuserapp.screens.shared.CustomOutlineBtn
import beresident.prototype.beresidentuserapp.screens.shared.CustomTopBar
import beresident.prototype.beresidentuserapp.screens.shared.CustomTxtField

class ForgotScreen (forgotViewModel: ForgotViewModel): ComponentActivity() {
    var forgot = forgotViewModel

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun Screen(navController: NavController){
        val emailState = remember { CustomTxtField() }

        val scaffoldState = rememberScaffoldState()
        val snackHostState = SnackbarHostState()

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
                    CustomTxtField(emailState, stringResource(R.string.email), btmPadding = DefaultTheme.dimens.grid_1_5)
                    Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_1))
                    Text(
                        stringResource(R.string.valid_email),
                        fontSize = 12.sp,
                        color = Grey,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 24.dp)
                    )
                    CustomOutlineBtn(
                        stringResource(R.string.send_verification_link),
                        textColor = Color.White,
                        background = MaterialTheme.colors.secondary,
                        action = {
                            forgot.onCreate(emailState.text, snackHostState, navController)
                        }
                    )
                }
            }
            BoxWithConstraints (
                Modifier
                    .fillMaxSize()
                    .padding(DefaultTheme.dimens.grid_2)){
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    beresident.prototype.beresidentuserapp.screens.shared.SnackbarHost(
                        snackHostState,
                    )
                }
            }
        }
    }
}


